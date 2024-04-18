package com.et.atomikos.controller;

import com.et.atomikos.mapper1.ManyService1;
import com.et.atomikos.mapper2.ManyService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloWorldController {

    @Autowired
    private ManyService1 manyService1;

    @Resource
    private ManyService2 manyService2;
    //http://localhost:8088/datasource1?userId=9&username=datasource1&age=2
    @RequestMapping(value = "datasource1")
    public int datasource1(String userId,String username, Integer age) {
        return manyService1.insert(userId,username, age);
    }
    //http://localhost:8088/datasource2?userId=9&username=datasource2&age=2
    @RequestMapping(value = "datasource2")
    public int datasource2(String userId,String username, Integer age) {
        return manyService2.insert(userId,username, age);
    }

    //http://localhost:8088/insertDb1AndDb2?userId=1&username=tom5&age=2
    //http://localhost:8088/insertDb1AndDb2?userId=2&username=tom5&age=0  //touch a error
    @RequestMapping(value = "insertDb1AndDb2")
    public int insertDb1AndDb2(String userId,String username, Integer age) {
        return manyService1.insertDb1AndDb2(userId,username, age);
    }

}