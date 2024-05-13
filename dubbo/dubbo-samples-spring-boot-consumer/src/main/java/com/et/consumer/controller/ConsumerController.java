package com.et.consumer.controller;


import com.et.api.UserService;
import com.et.api.entity.User;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ConsumerController {
    /**
     * 通过 @Reference注解调用远程服务，也就是服务提供者dubbo-provider的服务
     */
    @Reference
    private UserService userService;


    @GetMapping("/info")
    public String getUserById() {
        return userService.getUserInfo();
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

}

