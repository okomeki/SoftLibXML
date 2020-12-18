package net.siisise.xml;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * List,Mapを使ったElement
 */
public class XElement extends XNode<Element> {

    public XElement(Element e) {
        super(e);
    }

    public String getTagName() {
        return val.getTagName();
    }

    public void setAttribute(String name, String value) throws DOMException {
        val.setAttribute(name, value);
    }

    public void removeAttribute(String name) throws DOMException {
        val.removeAttribute(name);
    }

    public String getAttribute(String name) {
        return val.getAttribute(name);
    }

    public String getAttribute(String ns, String name) {
        return val.getAttributeNS(ns, name);
    }

    public boolean hasAttributes() {
        return val.hasAttributes();
    }

    public boolean hasAttribute(String name) {
        return val.hasAttribute(name);
    }

    public boolean hasAttribute(String ns, String name) {
        return val.hasAttributeNS(ns, ns);
    }

    /**
     * 階層構造を飛び越える (HTMLに最適、XMLには不適?)
     *
     * @param tagName
     * @return
     */
    public List<XElement> getElementsByTagName(String tagName) {
        return toList(val.getElementsByTagName(tagName));
    }

//    public List<XElement> getElementsByTagName(String ns, String tag) {
//        return toList(val.getElementsByTagNameNS(ns, tag));
//    }
    public List<XElement> getElements() {
        return elementStream().collect(Collectors.toList());
    }

    Stream<XElement> elementStream() {
        List<XNode> nodes = getChildNodes();
        return nodes.parallelStream().filter(e -> e instanceof XElement).map(e -> (XElement) e);
    }

    public List<XElement> getElements(String tag) {
        return elementStream().filter(e -> e.getTagName().equals(tag))
                .collect(Collectors.toList());
    }

    public List<XElement> getElements(String ns, String tag) {
        return elementStream()
                .filter(e -> e.equals(ns, tag))
                .collect(Collectors.toList());
    }

    public XElement getTag(String name) {
        return elementStream().filter(e -> e.getTagName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Documentでnamespaceが有効になっていること
     *
     * @param ns
     * @param name
     * @return
     */
    public XElement getTag(String ns, String name) {
        return elementStream().filter(e -> e.equals(ns, name)).findFirst().orElse(null);
    }

    boolean equals(String ns, String name) {
        return getNamespaceURI().equals(ns) && getLocalName().equals(name);
    }

}
