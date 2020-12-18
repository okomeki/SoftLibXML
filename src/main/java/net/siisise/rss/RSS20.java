package net.siisise.rss;

import java.util.ArrayList;
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

        Element node = doc.getDocumentElement();
        XElement xrss = (XElement) XNode.toObj(node);

        XElement xchannel = xrss.getTag("channel");
        ch.title = xchannel.getTag("title").getTextContent();
        ch.link = xchannel.getTag("link").getTextContent().trim();
        ch.pubDate = parseDate(xchannel.getTag("pubDate"));
        List<XElement> xitems = xchannel.getElementsByTagName("item");
        ch.items = new ArrayList();
        for (XElement xitem : xitems) {
            Item item = toItem(xitem);
            ch.items.add(item);
        }
        return ch;
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
