package com.et.freemaker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
    @GetMapping("/")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView("index/index");
        // add title to Model
        modelAndView.addObject("title", "Freemarker");
        return modelAndView;
    }
}