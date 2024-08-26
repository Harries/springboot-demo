package com.et.gzip.controller;

import com.et.gzip.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class HelloWorldController {
    @Autowired
    private RedisTemplate redisTemplateWithJackson;

    @PostMapping("/hello")
    public User showHelloWorld(@RequestBody User user){
        log.info("user:"+ user);

        return user;
    }
    @PostMapping("/redis")
    public User redis(@RequestBody User user){
        log.info("user:"+ user);
        redisTemplateWithJackson.opsForValue().set("user",user);
        User redisUser = (User) redisTemplateWithJackson.opsForValue().get("user");
        return redisUser;
    }
}