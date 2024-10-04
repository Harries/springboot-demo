package com.et.controller;

import com.et.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
	@Autowired
	HelloService helloService;
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(String name){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", helloService.sayhi(name));
        return map;
    }
}