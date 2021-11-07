package net.siisise.rss;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author okome
 */
public class RSS10Test {
    
    String[] rss = {
        "https://pc.watch.impress.co.jp/data/rss/1.0/pcw/feed.rdf",
        "https://www.soumu.go.jp/news.rdf", // 総務省
        "https://www.mhlw.go.jp/stf/news.rdf", // 厚生労働省
//        "https://www.fsa.go.jp/fsaNewsListAll_rss2.xml",
        "https://www.caa.go.jp/news.rss", // 消費者庁
        "https://ascii.jp/rss.xml",
        "https://ascii.jp/digital/rss.xml",
        "https://news.yahoo.co.jp/rss/topics/it.xml",
        "https://news.mynavi.jp/rss/digital/kaden/audio",
        "https://rss.itmedia.co.jp/rss/2.0/itmedia_all.xml",
        "https://www.gizmodo.jp/index.xml",
        "https://srad.jp/sradjp.rss"
// 読めない 2020-12-18
//        "https://www.e-gov.go.jp/news/news.xml",
//        "https://developer.e-gov.go.jp/contents/news/news.xml"
    };

    public RSS10Test() {
    }

    @Test
    public void testSomeMethod() throws URISyntaxException, ParserConfigurationException, SAXException, IOException {
        System.out.println("read");
        String[] argv = null;
        DateFormat df = DateFormat.getDateTimeInstance();
        
        Channel ch = new Channel();
        for ( String rsss : rss ) {
            URI uri = new URI(rsss);
            System.out.println(rsss);
            new RSS().read(ch, uri);
//          System.out.println(JSON2.valueOf(ch));
        }
        for ( Item item : ch.items ) {
            System.out.println("たいとる:" + item.title);
            System.out.println("りんく:" + item.link);
            if ( item.pubDate != null ) {
                System.out.println("にちじ:" + df.format(item.pubDate));
            }
            System.out.println(item.description);

        }
        
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
