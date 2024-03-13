package com.et.hazelcast.controller;

import com.et.hazelcast.entity.Employee;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloWorldController {
    @RequestMapping("/hello")
    @ResponseBody
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
    @Cacheable(value = "employee")
    @GetMapping("employee/{id}")
    @ResponseBody
    public Employee getSubscriber(@PathVariable("id") int id) throws
            InterruptedException {
        System.out.println("Finding employee information with id " + id + " ...");
        return new Employee(id, "John Smith", "CS");
    }
}