package com.et.jpa.controller;

import com.et.jpa.entity.User;
import com.et.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/hello")
    @ResponseBody
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }


    @RequestMapping("/add")
    public User add(String name){
        User user = new User();
        user.setName(name);
        return userRepository.save(user);
    }

    @RequestMapping("/list")
    public Iterable<User> list(){
        Iterable<User> all = userRepository.findAll();
        return all;
    }
}