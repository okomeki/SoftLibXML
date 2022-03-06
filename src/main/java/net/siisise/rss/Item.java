package net.siisise.rss;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
public class Item {
    public String title;
    public String link;
    public Date pubDate;
    public Date lastBuildDate;
    public String description;
    public Map<String,Object> map;

    /**
     * だいたい同じ判定
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.title);
        hash = 41 * hash + Objects.hashCode(this.link);
        hash = 41 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if ( o == null ) return false;
        if ( o instanceof Item ) {
            Item s = (Item)o;
            return ( strEq(s.title, title) && strEq(s.link, link) && strEq(s.description, description));
        }
        return false;
    }
    
    private static boolean strEq(String a, String b) {
        if ( a == null ) {
            return b == null;
        }
        return a.equals(b);
    }
}
