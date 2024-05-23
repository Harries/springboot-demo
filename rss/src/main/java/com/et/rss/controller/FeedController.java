package com.et.rss.controller;


import com.rometools.rome.feed.atom.*;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.SyndPerson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Date;


@RestController
@RequestMapping("/feed")
public class FeedController {


    @GetMapping(path = "/rss")
    public Channel rss() {
        Channel channel = new Channel();
        channel.setFeedType("rss_2.0");
        channel.setTitle("HBLOG Feed");
        channel.setDescription("Recent posts");
        channel.setLink("http://www.liuhaihua.cn");
        channel.setUri("http://www.liuhaihua.cn");
        channel.setGenerator("Harries");

        Image image = new Image();
        image.setUrl("/resources/assist/images/blog/b8fb228cdff44b8ea65d1e557bf05d2b.png");
        image.setTitle("HBLOG Feed");
        image.setHeight(32);
        image.setWidth(32);
        channel.setImage(image);

        Date postDate = new Date();
        channel.setPubDate(postDate);

        Item item = new Item();
        item.setAuthor("Harries");
        item.setLink("http://www.liuhaihua.cn");
        item.setTitle("Spring Boot integrated banner   quick start demo");
        item.setUri("http://www.liuhaihua.cn/archives/710608.html");
        item.setComments("ttp://www.liuhaihua.cn/archives/710608.html#commnet");

        com.rometools.rome.feed.rss.Category category = new com.rometools.rome.feed.rss.Category();
        category.setValue("CORS");
        item.setCategories(Collections.singletonList(category));

        Description descr = new Description();
        descr.setValue("pring Boot Banner is a feature for displaying custom ASCII art and information at application startup. This ASCII art usually includes the project name, version number, author information");
        item.setDescription(descr);
        item.setPubDate(postDate);

        channel.setItems(Collections.singletonList(item));
        //Like more Entries here about different new topics
        return channel;
    }

    @GetMapping(path = "/atom")
    public Feed atom() {
        Feed feed = new Feed();
        feed.setFeedType("atom_1.0");
        feed.setTitle("HBLOG");
        feed.setId("http://www.liuhaihua.cn");

        Content subtitle = new Content();
        subtitle.setType("text/plain");
        subtitle.setValue("recents post");
        feed.setSubtitle(subtitle);

        Date postDate = new Date();
        feed.setUpdated(postDate);

        Entry entry = new Entry();

        Link link = new Link();
        link.setHref("http://www.liuhaihua.cn/archives/710608.html");
        entry.setAlternateLinks(Collections.singletonList(link));
        SyndPerson author = new Person();
        author.setName("HBLOG");
        entry.setAuthors(Collections.singletonList(author));
        entry.setCreated(postDate);
        entry.setPublished(postDate);
        entry.setUpdated(postDate);
        entry.setId("710608");
        entry.setTitle("Spring Boot integrated banner   quick start demo");

        Category category = new Category();
        category.setTerm("CORS");
        entry.setCategories(Collections.singletonList(category));

        Content summary = new Content();
        summary.setType("text/plain");
        summary.setValue("pring Boot Banner is a feature for displaying custom ASCII art and information at application startup. This ASCII art usually includes the project name, version number, author information");
        entry.setSummary(summary);

        feed.setEntries(Collections.singletonList(entry));
        //add different topic
        return feed;
    }

}
