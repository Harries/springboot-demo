package com.et.controller;

import com.et.service.HelloZillizVectorDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
	@Autowired
	HelloZillizVectorDBService helloZillizVectorDBService;
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
		helloZillizVectorDBService.search();
        map.put("msg", "HelloWorld");
        return map;
    }
}