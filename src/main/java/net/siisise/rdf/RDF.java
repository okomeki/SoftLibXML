package net.siisise.rdf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.siisise.xml.XElement;
import org.w3c.dom.Document;

/**
 * RSS 1.0の基にするだけ
 */
public class RDF {
    private final XElement top;
    
    public static final String RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    
    Map<String,XElement> rdfs = new HashMap<>();

    public RDF(Document doc) {
        top = new XElement(doc.getDocumentElement());

        if ( !top.getNamespaceURI().equals(RDF) || !top.getLocalName().equals( "RDF") ) {
            throw new UnsupportedOperationException();
        }
        List<XElement> childs = top.getElements();
        for ( XElement ch : childs ) {
            String about = ch.getAttribute( RDF, "about" );
            if ( about != null ) {
                rdfs.put(about, (XElement)ch);
            }
        }
    }

    public XElement top() {
        return top;
    }
    
    public XElement get(String key) {
        return rdfs.get(key);
    }
    
    public List<XElement> getTags(String tag) {
        return top.getElements(tag);
    }

    public List<XElement> getTags(String ns, String tag) {
        return top.getElements(ns, tag);
    }
}
