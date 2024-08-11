package com.et.desensization.controller;

import com.et.desensization.dto.Teacher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.zyc.desensitization.Sensitive;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public Teacher showHelloWorld(){

		Teacher teacher = Sensitive.desensitize(new Teacher(1,"小明老师","400000000000000000","15900000000","1420000000@qq.com","123456789"));
		return teacher;
    }
}