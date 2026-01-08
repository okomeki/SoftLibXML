package net.siisise.xml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML Node
 *
 * @param <N> Node継承型
 */
public class XNode<N extends Node> {

    N val;

    public XNode(N node) {
        val = node;
    }

    public N getNode() {
        return val;
    }

    public static XNode toObj(Node item) {
        if (item == null) {
            return null;
        }
        short nt = item.getNodeType();

        switch (nt) {
            case Node.ELEMENT_NODE:
                return new XElement((Element) item);
            case Node.TEXT_NODE:
                return new XText(item);
            default:
                return new XNode(item);
        }
    }

    /**
     * null対策
     *
     * @param xn XNode
     * @return Node
     */
    public static Node toNode(XNode xn) {
        return (xn == null) ? null : xn.getNode();
    }

    /**
     * List変換.
     * @param <E>
     * @param nl XML nodelist
     * @return Java XNode List
     */
    public static <E extends XNode> List<E> toList(NodeList nl) {
        int len = nl.getLength();
        List<E> nll = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            nll.add((E) toObj(nl.item(i)));
        }
        return nll;
    }

    public String getNamespaceURI() {
        return val.getNamespaceURI();
    }

    /**
     * Node Name
     *
     * @return node name
     */
    public String getName() {
        return val.getNodeName();
    }

    /**
     * Node Name
     *
     * @return node name
     * @deprecated #getName()
     */
    @Deprecated
    public String getNodeName() {
        return val.getNodeName();
    }

    public String getLocalName() {
        return val.getLocalName();
    }

    /**
     * テキストではない
     *
     * @return node value
     */
    public String getValue() {
        return val.getNodeValue();
    }

    /**
     * textContent
     * @return text content
     */
    public String getTextContent() {
        return val.getTextContent();
    }

    public void setTextContent(String text) {
        val.setTextContent(text);
    }

    /**
     * List化 child node
     * @param <E>
     * @return child node list
     */
    public <E extends XNode> List<E> getChildNodes() {
        return toList(val.getChildNodes());
    }

    /**
     * 先頭.
     * @return 先頭Node
     */
    public XNode getFirstChild() {
        return toObj(val.getFirstChild());
    }

    public XNode getLastChild() {
        return toObj(val.getLastChild());
    }

    public void append(XNode node) {
        val.appendChild(node.getNode());
    }

    /**
     * 特定位置に追加.
     * @param index
     * @param node 
     */
    public void append(int index, XNode node) {
        NodeList nl = val.getChildNodes();
        if (nl.getLength() < index) { // 後ろへ
            val.appendChild(node.val);
        } else {
            Node ref = nl.item(index);
            val.insertBefore(node.val, ref);
        }
    }

    public void insertBefore(XNode newChild, XNode refChild) {
        val.insertBefore(newChild.getNode(), toNode(refChild));
    }

    public void remove(XNode child) {
        val.removeChild(child.getNode());
    }

    /**
     * NamedNodeMap を Java Mapに.
     * 同じ名前の重複には対応不可.
     * @return Java Map
     */
    public Map<String, String> getAttributes() {
        Map<String, String> map = new LinkedHashMap();
        NamedNodeMap nm = val.getAttributes();
        int len = nm.getLength();
        for (int i = 0; i < len; i++) {
            Node attr = nm.item(i);
            String name = attr.getNodeName();
            String value = attr.getNodeValue();
            map.put(name, value);
        }
        return map;
    }

    public Map<String, String> getAttributes(String ns) {
        Map<String, String> map = new LinkedHashMap();
        NamedNodeMap nm = val.getAttributes();
        int len = nm.getLength();
        for (int i = 0; i < len; i++) {
            Node attr = nm.item(i);
            String uri = attr.getNamespaceURI();
            if (ns.equals(uri)) {
                String name = attr.getNodeName();
                String value = attr.getNodeValue();
                map.put(name, value);
            }
        }
        return map;
    }

}
