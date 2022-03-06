package net.siisise.atom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.siisise.rss.Channel;
import net.siisise.rss.Item;
import net.siisise.rss.RSS;
import net.siisise.xml.XElement;
import net.siisise.xml.XNode;
import org.w3c.dom.Document;

/**
 * The Atom Syndication Format と The Atom Publishing Protocol の2種類あるらしい。 
 */
public class Atom {
    
    static final String MEDIA = "http://search.yahoo.com/mrss/";

    /**
     * Atom を RSS に読み替える
     * @param ch
     * @param doc Atom XML
     */
    public void read(Channel ch, Document doc) {
        XElement xfeed = (XElement) XNode.toObj(doc.getDocumentElement());
        ch.title = xfeed.getTag("title").getTextContent();
        System.out.println(ch.title);
        ch.lastBuildDate = RSS.parseDate(xfeed.getTag("updated"));
        ch.pubDate = RSS.parseDate(xfeed.getTag("published"));
        if ( ch.pubDate == null ) {
            ch.pubDate = new Date();
        }
        List<XElement> entries = xfeed.getElements("entry");
        List<Item> items = new ArrayList<>();
        for ( XElement entry : entries ) {
            items.add(toItem(entry));
        }
        new RSS().merge(ch.items, items);
    }
    
    Item toItem(XElement entry) {
        Item item = new Item();
        item.lastBuildDate = RSS.parseDate(entry.getTag("updated"));
        item.pubDate = RSS.parseDate(entry.getTag("published"));
        if ( item.pubDate == null ) {
            item.pubDate = new Date();
        }
        item.title = entry.getTag("title").getTextContent();
        XElement summary = entry.getTag("summary");
        if ( summary != null ) {
            item.description = summary.getTextContent();
        } else { // YouTube
            XElement group = entry.getTag(MEDIA,"group");
            XElement description = group.getTag(MEDIA,"description");
            item.description = "<pre>" + description.getTextContent() + "</pre>";
        }
        item.link = entry.getTag("link").getAttribute("href");
        return item;
    }
}
