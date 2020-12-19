package net.siisise.rss;

import java.util.ArrayList;
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
    public Date updateBase;
    
    // 2.0
    public Date pubDate;
    public Date lastBuildDate;
    public int ttl;
    
    public List<Item> items = new ArrayList<>();

    public Map<String,Object> map;
}
