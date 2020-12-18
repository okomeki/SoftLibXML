package net.siisise.atom;

import java.util.List;
import net.siisise.rss.Channel;
import net.siisise.xml.XElement;
import net.siisise.xml.XNode;
import org.w3c.dom.Document;

/**
 * The Atom Syndication Format と The Atom Publishing Protocol の2種類あるらしい。 
 * @author okome
 */
public class Atom {
    
    public Feed read(Document doc) {
        
        throw new UnsupportedOperationException();
    }
    
    /**
     * Atom を RSS に読み替える
     * @param doc Atom XML
     * @return RSS Channel風
     */
    public Channel readChannel(Document doc) {
        Channel ch = new Channel();
        XElement xfeed = (XElement) XNode.toObj(doc.getDocumentElement());
        List<XElement> entries = xfeed.getElements("entry");
        throw new UnsupportedOperationException();
    }
}
