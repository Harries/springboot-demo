package com.et.disruptor.controller;

import com.et.disruptor.event.HelloEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloWorldController {
    @Autowired
    HelloEventProducer helloEventProducer;
    @RequestMapping("/hello")
    @ResponseBody
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
    @RequestMapping("/send")
    @ResponseBody
    public String  add(String message){
        helloEventProducer.sayHelloMq(message);
        return "success";
    }

}