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
	@Value("normal")
	private String normal; // Inject ordinary string

	@Value("#{systemProperties['os.name']}")
	private String systemPropertiesName; //Inject operating system properties

	@Value("#{ T(java.lang.Math).random() * 100.0 }")
	private double randomNumber; //Inject expression result

	@Value("#{beanInject.another}")
	private String fromAnotherBean; // Inject other Bean attributes: Inject the attribute another of the beanInject object. See the specific definition of the class below.

	@Value("classpath:config.txt")
	private Resource resourceFile; // Inject file resources

	@Value("http://www.baidu.com")
	private Resource testUrl; // Inject URL resources

    @Value("#{'${server.name}'.split(',')}")
    private List<String> servers;

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