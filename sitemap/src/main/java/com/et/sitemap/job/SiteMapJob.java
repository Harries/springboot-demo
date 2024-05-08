package com.et.sitemap.job;

import com.redfin.sitemapgenerator.SitemapIndexGenerator;
import com.redfin.sitemapgenerator.W3CDateFormat;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName SiteMapJob
 * @Description todo
 * @date 2024年04月25日 17:44
 */
@Component
public class SiteMapJob {
    private Logger log = LoggerFactory.getLogger(getClass());

    //@Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(initialDelay = 1000,fixedRate = 10000)
    public void generateSitemap() {
        log.info("start generate sitemap");
        String tempPath = "D://tmp/";
        File file = new File(tempPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String domain = "http://www.liuhaihua.cn";
        try {
            WebSitemapGenerator g1 = WebSitemapGenerator.builder(domain, file).maxUrls(10000)
                    .fileNamePrefix("article").build();
            Date date = new Date();
            for (int i = 1; i < 160000; i++) {
                WebSitemapUrl url = new WebSitemapUrl.Options(domain + "/article/" + i).lastMod(date).build();
                g1.addUrl(url);
            }

            WebSitemapGenerator g2 = WebSitemapGenerator.builder(domain, file)
                    .fileNamePrefix("tag").build();
            Date date2 = new Date();
            for (int i = 1; i < 21; i++) {
                WebSitemapUrl url = new WebSitemapUrl.Options(domain + "/tag/" + i).lastMod(date2).build();
                g2.addUrl(url);
            }

            WebSitemapGenerator g3 = WebSitemapGenerator.builder(domain, file)
                    .fileNamePrefix("type").build();
            Date date3 = new Date();
            for (int i = 1; i < 21; i++) {
                WebSitemapUrl url = new WebSitemapUrl.Options(domain + "/type/" + i).lastMod(date3).build();
                g3.addUrl(url);
            }




            List<String> fileNames = new ArrayList<>();

            // 生成 sitemap 文件
            List<File> articleFiles = g1.write();
            articleFiles.forEach(e -> fileNames.add(e.getName()));
            List<File> tagFiles = g2.write();
            tagFiles.forEach(e -> fileNames.add(e.getName()));
            List<File> typeFiles = g3.write();
            typeFiles.forEach(e -> fileNames.add(e.getName()));

            // 构造 sitemap_index 生成器
            W3CDateFormat dateFormat = new W3CDateFormat(W3CDateFormat.Pattern.DAY);
            SitemapIndexGenerator sitemapIndexGenerator = new SitemapIndexGenerator
                    .Options(domain, new File(tempPath + "sitemap_index.xml"))
                    .dateFormat(dateFormat)
                    .autoValidate(true)
                    .build();

            fileNames.forEach(e -> {
                try {
                    // 组装 sitemap 文件 URL 地址
                    sitemapIndexGenerator.addUrl(domain + "/" + e);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            });

            // 生成 sitemap_index 文件
            sitemapIndexGenerator.write();
            log.info("end generate sitemap");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
