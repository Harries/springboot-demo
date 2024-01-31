package com.et.graylog.controller;

import com.et.graylog.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Controller
public class HelloWorldController {
    @Autowired
    DemoService demoService;

    @RequestMapping("/hello")
    @ResponseBody
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        log.info("my name is HelloWorldController");
        demoService.hello();
        return map;
    }

    @GetMapping("log")
    public String log(){

        log.error("error");
        log.warn("warn");
        log.info("info");
        log.debug("debug");
        int i = 1/0;
        return "success";
    }
}