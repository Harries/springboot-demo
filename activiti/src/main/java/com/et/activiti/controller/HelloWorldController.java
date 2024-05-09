package com.et.activiti.controller;

import com.et.activiti.service.ActivityConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @Autowired
    ActivityConsumerService activityConsumerService;

    @RequestMapping(value="/startActivityDemo",method= RequestMethod.GET)
    public boolean startActivityDemo(){
        return activityConsumerService.startActivityDemo();
    }
}