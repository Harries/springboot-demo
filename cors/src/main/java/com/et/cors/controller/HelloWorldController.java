package com.et.cors.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    //@CrossOrigin
    @GetMapping("/hello")
    public String hello() {
        System.out.println("get hello");
        return "get hello";
    }

    @CrossOrigin
    @PostMapping("/hello")
    public String hello2() {
        System.out.println("post hello");
        return "post hello";

    }
}