package com.et.mfa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
public class IndexController {

    @GetMapping("secured")
    String index() {
        return "secured";
    }

    @GetMapping("/tes")
    String index2() {
        return "Test Google Authentication! " + new Date();
    }
}
