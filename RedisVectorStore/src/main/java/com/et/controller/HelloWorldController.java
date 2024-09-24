package com.et.controller;

import com.et.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.et.service.SearchService;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @Autowired
    SearchService searchService;
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", searchService.retrieve("beer"));
        return map;
    }
}