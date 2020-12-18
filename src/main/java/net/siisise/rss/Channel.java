package net.siisise.rss;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * RSS の だいたいこんな感じのチャンネル
 */
public class Channel {
    // id
    public String about;

    public String title;
    public String link;
    public String description;
    
    // 2.0
    public Date pubDate;
    public Date lastBuildDate;
    
    public List<Item> items;

    public Map<String,Object> map;
}
