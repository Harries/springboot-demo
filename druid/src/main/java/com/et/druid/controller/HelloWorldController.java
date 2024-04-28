package com.et.druid.controller;

import com.et.druid.entity.UserInfo;
import com.et.druid.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloWorldController {
    @Autowired
    UserInfoMapper userInfoMapper;
    @RequestMapping("/hello")
    public  List<UserInfo> showHelloWorld(){
        List<UserInfo>  list = userInfoMapper.selectAll();
        return list;
    }
}