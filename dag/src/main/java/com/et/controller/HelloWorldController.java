package com.et.controller;

import com.et.service.ServiceTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.et.service.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @Autowired
    ServiceOne ServiceOne;
    @Autowired
    ServiceTwo ServiceTwo;
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
}