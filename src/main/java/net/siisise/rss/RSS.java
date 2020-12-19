package net.siisise.rss;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
    
    // 広告を除くサイズ
    int max = 200;

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
    
    public RSS(int size) {
        max = size;
    }
    
    public RSS() {
    }

    public Channel read(URI uri) throws ParserConfigurationException, SAXException, IOException {
        Document doc = XMLIO.readXML(uri);
        return read(doc);
    }
    
    public void read(Channel ch, URI uri) throws ParserConfigurationException, SAXException, IOException {
        Document doc = XMLIO.readXML(uri);
        read(ch, doc);
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
        Channel ch = new Channel();
        read(ch,doc);
        return ch;
    }
    
    /**
     * 
     * @param ch item
     * @param doc 
     */
    void read(Channel ch, Document doc) {
        Element ele = doc.getDocumentElement();
        XElement xdoc = (XElement) XNode.toObj(ele);
        if ( xdoc.getName().equals("rdf:RDF") ) {
            // RDF 形式 (RSS 1.0など)
            new RSS10().read(ch, doc);
        } else if ( xdoc.getName().equals("rss") && xdoc.hasAttribute("version")) {
            String ver = xdoc.getAttribute("version");
            if ("2.0".equals(ver)) {
                new RSS20().read(ch, doc);
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
    
    public void merge(List<Item> items, List<Item> newItems) {
        merge(items, newItems, max);
    }
    

    public void merge(List<Item> items, List<Item> newItems, int max ) {
        int len = newItems.size();
        for (int i = len -1; i >=0; i-- ) {
            Item item = newItems.get(i);
            if ( !items.contains(item) ) {
                items.add(0,item);
            }
        }
        // 重複する広告以外を古い方から消す 広告は後ろの方に残るのでMAX件以内でループする広告は先頭には出てこない
        if ( items.size() > max ) {
            for ( int i = items.size() - 1; i >= max; i-- ) {
                Item over = items.get(i);
                if ( !newItems.contains(over) ) {
                    items.remove(over);
                }
            }
        }
    }
    
    public void sort(List<Item> items) {
        Collections.sort(items, (Item o1, Item o2) -> o2.pubDate.compareTo(o1.pubDate));
        
    }
}
