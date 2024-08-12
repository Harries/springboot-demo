package com.et.sentinel.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.et.sentinel.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @Autowired
    TestService testService;
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld()  {

        Map<String, Object> map = new HashMap<>();
        try {
            map.put("msg", testService.sayHello("harries"));
        }catch (Exception e){
            System.out.println("sayHello blocked!");
        }
        return map;
    }
}