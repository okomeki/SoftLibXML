package net.siisise.rss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.siisise.rdf.RDF;
import net.siisise.xml.XElement;
import org.w3c.dom.Document;

/**
 * RSS 1.0 のテスト
 * RDF Site Summary
 * RDF Site Syndication ?
 * Really Simple Syndication ?
 *
 * XML DocumentBuilderFactoryで namespace必須。
 */
public class RSS10 extends RSS {

    static final String RSS = "http://purl.org/rss/1.0/";
    static final String DC = "http://purl.org/dc/elements/1.1/";
//    static final String CONTENT = "http://purl.org/rss/1.0/modules/content/";
//    static final String SYN = "http://purl.org/rss/1.0/modules/syndication/";

    public RSS10() {

    }

    @Override
    Channel read(Document doc) {
        Channel ch = new Channel();
        RDF rdf = new RDF(doc);
        XElement xch = rdf.getTags("channel").get(0);
        // てきとーにMap化
        ch.map = map(xch.getElements());

        ch.about = xch.getAttribute(RDF.RDF, "about");
        ch.title = (String) ch.map.get("title");
        ch.link = ((String) ch.map.get("link")).trim();
        ch.description = (String) ch.map.get("description");
        System.out.println(ch.title);
        // dc:date ないこともある
        ch.pubDate = parseDate(xch.getTag(DC, "date"));

        ch.items = new ArrayList<>();

        XElement seq = xch.getTag("items").getTag(RDF.RDF, "Seq");
        List<XElement> itemrefs = seq.getElements(RDF.RDF, "li");
        for (XElement n : itemrefs) {
            String key = n.getAttribute(RDF.RDF, "resource"); // 直接itemの場合もあるかもしれない
            XElement xitem = rdf.get(key);
            Item item = toItem(xitem);
            ch.items.add(item);
        }

        return ch;
    }

    Item toItem(XElement xml) {
        Item item = new Item();
        item.map = map(xml.getElements());
        item.title = ((String) item.map.get("title")).trim();
        item.link = ((String) item.map.get("link")).trim();
        item.description = (String) item.map.get("description");
        item.pubDate = parseDate(xml.getTag(DC, "date"));
        return item;
    }

    static Map<String, Object> map(List<XElement> eles) {
        Map map = new HashMap();
        for (XElement ele : eles) {
            // XMLの名前重複はListにする
            String name = ele.getNodeName();
            Object val;
            if (ele.getElements().isEmpty()) {
                val = ele.getTextContent();
            } else {
                val = ele;
            }
            Object v = map.get(name);
            if (v == null) {
                map.put(name, val);
            } else if (v instanceof List) {
                ((List) v).add(val);
            } else {
                List list = new ArrayList();
                list.add(v);
                list.add(val);
                map.put(name, list);
            }
        }
        return map;
    }

}
