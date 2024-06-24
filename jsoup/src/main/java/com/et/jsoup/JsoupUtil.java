package com.et.jsoup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName JsoupUtil
 * @Description todo
 * @date 2024/06/24/ 9:16
 */

public class JsoupUtil {
        public static Map<String ,Object> parseHtml(String url){
            Map<String,Object> map = new HashMap<>();
            //1.生成httpclient，相当于该打开一个浏览器
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            //2.创建get请求，相当于在浏览器地址栏输入 网址
            HttpGet request = new HttpGet(url);
            //设置请求头，将爬虫伪装成浏览器
            request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
//        HttpHost proxy = new HttpHost("60.13.42.232", 9999);
//        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//        request.setConfig(config);
            try {
                //3.执行get请求，相当于在输入地址栏后敲回车键
                response = httpClient.execute(request);

                //4.判断响应状态为200，进行处理
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    //5.获取响应内容
                    HttpEntity httpEntity = response.getEntity();
                    String html = EntityUtils.toString(httpEntity, "utf-8");
                    System.out.println(html);

                    /**
                     * 下面是Jsoup展现自我的平台
                     */
                    //6.Jsoup解析html
                    Document document = Jsoup.parse(html);
                    //像js一样，通过标签获取title
                    System.out.println(document.getElementsByTag("title").first());
                    Elements blogmain = document.getElementsByClass("col-sm-8 blog-main");


                    //像js一样，通过class 获取列表下的所有博客
                    Elements postItems =  blogmain.first().getElementsByClass("fade-in");
                    //循环处理每篇博客
                    List<Map>  list =  new ArrayList<>();
                    for (Element postItem : postItems) {
                        Map<String,Object> row = new HashMap<>();
                        //像jquery选择器一样，获取文章标题元素
                        Elements titleEle = postItem.select(".entry-title a");
                        System.out.println("文章标题:" + titleEle.text());;
                        row.put("title",titleEle.text());
                        System.out.println("文章地址:" + titleEle.attr("href"));
                        row.put("href",titleEle.attr("href"));
                        //像jquery选择器一样，获取文章作者元素
                        Elements footEle = postItem.select(".archive-content");
                        System.out.println("文章概要:" + footEle.text());;
                        row.put("summary",footEle.text());
                        Elements view = postItem.select(".views");
                        System.out.println( view.text());
                        row.put("views",view.text());
                        System.out.println("*********************************");
                        list.add(row);
                    }
                    map.put("data",list);

                } else {
                    //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                    System.out.println("返回状态不是200");
                    System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //6.关闭
                HttpClientUtils.closeQuietly(response);
                HttpClientUtils.closeQuietly(httpClient);
            }
            return  map;
        }
        public static void main(String[] args) {
            parseHtml("http://www.liuhaihua.cn/");
        }

}
