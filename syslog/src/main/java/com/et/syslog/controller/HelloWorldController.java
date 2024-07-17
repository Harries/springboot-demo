package com.et.syslog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    private final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(@RequestParam("n") String name){
        logger.info("Say hello to {}", name);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
}