package com.et.sitemap;

import com.et.sitemap.job.SiteMapJob;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.W3CDateFormat;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.TimeZone;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoTests {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    SiteMapJob siteMapJob;
    @Before
    public void before()  {
        log.info("init some data");
    }
    @After
    public void after(){
        log.info("clean some data");
    }
    @Test
    public void execute() throws MalformedURLException {
        log.info("your method test Code");
        siteMapJob.generateSitemap();

    }

}

