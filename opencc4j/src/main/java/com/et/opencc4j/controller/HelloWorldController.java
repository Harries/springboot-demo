package com.et.opencc4j.controller;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping("/toSimple")
    public Map<String, Object> toSimple(String original){
        Map<String, Object> map = new HashMap<>();
        String result = ZhConverterUtil.toSimple(original);
        map.put("original", original);
        map.put("Simple", result);
        return map;
    }
    @RequestMapping("/toTraditional")
    public Map<String, Object> toTraditional(String original){
        Map<String, Object> map = new HashMap<>();
        String result = ZhConverterUtil.toTraditional(original);
        map.put("original", original);
        map.put("Traditional", result);
        return map;
    }
}