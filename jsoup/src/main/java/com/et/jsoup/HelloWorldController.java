package com.et.jsoup;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map =JsoupUtil.parseHtml("http://www.liuhaihua.cn/");
        map.put("msg", "HelloWorld");
        return map;
    }
}