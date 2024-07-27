package com.et.echats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelloWorldController {
    @RequestMapping("/")
    public String index()
    {
        return"index";
    }

}