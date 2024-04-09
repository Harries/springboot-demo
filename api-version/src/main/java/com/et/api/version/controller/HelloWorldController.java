package com.et.api.version.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class HelloWorldController {
    //@GetMapping(params = "version=1")
    @GetMapping(produces = "application/vnd.company.app-v1+json")
    //@GetMapping(headers = "API-Version=1")
    public Map<String, Object> showHelloWorldone(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld1");
        return map;
    }
    //@GetMapping(params = "version=2")
    @GetMapping(produces = "application/vnd.company.app-v2+json")
    //@GetMapping(headers = "API-Version=2")
    public Map<String, Object> showHelloWorldtwo(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld2");
        return map;
    }


}