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
 *
 * @param <N>
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
        short nt = item.getNodeType();
        
        switch (nt) {
            case Node.ELEMENT_NODE:
                return new XElement((Element)item);
            case Node.TEXT_NODE:
                return new XText(item);
            default:
                return new XNode(item);
        }
    }
    
    public static <E extends XNode> List<E> toList(NodeList nl) {
        int len = nl.getLength();
        List<E> nll = new ArrayList<>(len);
        for ( int i = 0; i < len; i++) {
            nll.add((E)toObj(nl.item(i)));
        }
        return nll;
    }
    
    public String getNamespaceURI() {
        return val.getNamespaceURI();
    }

    public String getName() {
        return val.getNodeName();
    }
    
    public String getNodeName() {
        return val.getNodeName();
    }
    
    /**
     * テキストではない
     * @return 
     */
    public String getValue() {
        return val.getNodeValue();
    }
    
    public String getTextContent() {
        return val.getTextContent();
    }
    
    public String getLocalName() {
        return val.getLocalName();
    }
    
    public <E extends XNode> List<E> getChildNodes() {
        return toList(val.getChildNodes());
    }
    
    public Map<String,String> getAttributes() {
        Map<String,String> map = new LinkedHashMap();
        NamedNodeMap nm = val.getAttributes();
        int len = nm.getLength();
        for ( int i = 0; i < len; i++ ) {
            Node attr = nm.item(i);
            String name = attr.getNodeName();
            String value = attr.getNodeValue();
            map.put(name, value);
        }
        return map;
    }
    
    public Map<String,String> getAttributes(String ns) {
        Map<String,String> map = new LinkedHashMap();
        NamedNodeMap nm = val.getAttributes();
        int len = nm.getLength();
        for ( int i = 0; i < len; i++ ) {
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
