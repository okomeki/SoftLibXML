package net.siisise.rss;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.parsers.ParserConfigurationException;
import net.siisise.atom.Atom;
import net.siisise.atom.Feed;
import net.siisise.xml.XElement;
import net.siisise.xml.XMLIO;
import net.siisise.xml.XNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 */
public class RSS {

    static String[] DATEFORMATS = {
        "yyyy-MM-dd'T'HH:mm:ss.SSSX",
        "yyyy-MM-dd'T'HH:mm:ssX",
        "EEE, dd MMM yyyy HH:mm:ss zzz"
    };

    static final SimpleDateFormat[] dateFormats;
    
    static {
        dateFormats = new SimpleDateFormat[DATEFORMATS.length];
        for ( int i = 0; i < DATEFORMATS.length; i++) {
            dateFormats[i] = new SimpleDateFormat(DATEFORMATS[i], Locale.ENGLISH);
        }
    }

    public Channel read(URI uri) throws ParserConfigurationException, SAXException, IOException {
        Document doc = XMLIO.readXML(uri);
        return read(doc);
    }
    
    // ABNFで検証する?
    static Date parseDate(XElement xdate) {
        if ( xdate != null ) {
            String txt = xdate.getTextContent();
            for ( SimpleDateFormat df : dateFormats ) {
                try {
                    return df.parse(txt);
                } catch (ParseException ex) {
                }
            }
//            System.out.println("date null" + xdate.getTextContent());
        }
        return null;
    }

    Channel read(Document doc) {
        Element ele = doc.getDocumentElement();
        XElement xdoc = (XElement) XNode.toObj(ele);
        if ( xdoc.getName().equals("rdf:RDF") ) {
            // RDF 形式 (RSS 1.0など)
            return new RSS10().read(doc);
        } else if ( xdoc.getName().equals("rss") && xdoc.hasAttribute("version")) {
            String ver = xdoc.getAttribute("version");
            if ("2.0".equals(ver)) {
                return new RSS20().read(doc);
            } else {
                throw new UnsupportedOperationException();
            }
        } else if (xdoc.getName().equals("feed")){
            Feed feed = new Atom().read(doc);
            throw new UnsupportedOperationException();
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
