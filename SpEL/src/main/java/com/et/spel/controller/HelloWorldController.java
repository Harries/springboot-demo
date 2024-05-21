package com.et.spel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloWorldController {
    // 注入普通字符串
    @Value("normal")
    private String normal;
    // 注入操作系统属性
    @Value("#{systemProperties['os.name']}")
    private String systemPropertiesName;

    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private double randomNumber; //注入表达式结果
    // 注入其他Bean属性：注入beanInject对象的属性another，类具体定义见下面
    @Value("#{beanInject.another}")
    private String fromAnotherBean;
    // 注入文件资源
    @Value("classpath:config.txt")
    private Resource resourceFile;
    @Value("#{'${server.name}'.split(',')}")
    private List<String> servers;
    @Value("http://www.baidu.com")
    // 注入URL资源
    private Resource testUrl;
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("normal", normal);
        map.put("systemPropertiesName", systemPropertiesName);
        map.put("randomNumber", randomNumber);
        map.put("fromAnotherBean", fromAnotherBean);
       // map.put("resourceFile", resourceFile);
        //map.put("testUrl", testUrl);
        map.put("servers", servers);
        return map;
    }
}