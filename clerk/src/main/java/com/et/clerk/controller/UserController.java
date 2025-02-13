package com.et.clerk.controller;

import com.et.clerk.service.ClerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ClerkService clerkService;

    @GetMapping("/{userId}")
    public String getUserInfo(@PathVariable String userId) throws IOException {
        return clerkService.getUserInfo(userId);
    }
}