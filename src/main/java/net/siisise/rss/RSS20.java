package net.siisise.rss;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.siisise.xml.XElement;
import net.siisise.xml.XNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * https://cyber.harvard.edu/rss/rss.html
 *
 * @author okome
 */
public class RSS20 extends RSS {

    public RSS20() {
    }

    @Override
    Channel read(Document doc) {
        Channel ch = new Channel();
        read(ch, doc);
        return ch;
    }
    
    @Override
    void read(Channel ch, Document doc) {
        Element node = doc.getDocumentElement();
        XElement xrss = (XElement) XNode.toObj(node);

        XElement xchannel = xrss.getTag("channel");
        ch.title = xchannel.getTag("title").getTextContent();
        ch.link = xchannel.getTag("link").getTextContent().trim();
        ch.pubDate = parseDate(xchannel.getTag("pubDate"));
        if ( ch.pubDate == null ) {
            ch.pubDate = new Date();
        }
        XElement xttl = xchannel.getTag("ttl");
        if ( xttl != null ) {
            ch.ttl = Integer.parseInt(xttl.getTextContent().trim());
        }
        List<XElement> xitems = xchannel.getElements("item");
        List<Item> newItems = new ArrayList<>();
        for (XElement xitem : xitems) {
            Item item = toItem(xitem);
            if ( item.pubDate == null ) {
                item.pubDate = ch.pubDate;
            }
            newItems.add(item);
        }
        merge(ch.items, newItems);
    }

    Item toItem(XElement xitem) {
        Item item = new Item();
        item.title = xitem.getTag("title").getTextContent().trim();
        item.link = xitem.getTag("link").getTextContent().trim();
        XElement xdescription = xitem.getTag("description");
        if ( xdescription != null ) { // ITmedia が null
            item.description = xdescription.getTextContent();
        } else {
            item.description = "";
        }
        item.pubDate = parseDate(xitem.getTag("pubDate"));
        item.lastBuildDate = parseDate(xitem.getTag("lastBuildDate"));
        return item;
    }
}
