package com.et.swagger.controller;

import com.et.swagger.model.LoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "HelloWorldController", tags = "HelloWorldController", description = "this is a test")
public class HelloWorldController {
    @GetMapping("/hello")
    @ApiOperation("showHelloWorld")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        LoginDto loginDto =  new LoginDto();
        loginDto.setPassword("123456");
        loginDto.setPhone("11111123123");
        map.put("loginuser", loginDto);
        return map;
    }
    @PostMapping("/login_auth")
    @ApiOperation("login")
    public LoginDto login(@RequestBody LoginDto loginDto){
        return loginDto;
    }
}