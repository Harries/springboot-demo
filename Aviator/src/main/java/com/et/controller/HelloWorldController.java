package com.et.controller;

import com.et.annotation.Check;
import com.et.exception.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
    @GetMapping("/simple")
    @Check(ex = "name != null", msg = "Name cannot be empty")
    @Check(ex = "age != null", msg = "Age cannot be empty")
    @Check(ex = "age > 18", msg = "Age must be over 18 years old")
    @Check(ex = "phone != null", msg = "phone cannot be empty")
    @Check(ex = "phone =~ /^(1)[0-9]{10}$/", msg = "The phone number format is incorrect")
    @Check(ex = "string.startsWith(phone,\"1\")", msg = "The phone number must start with 1")
    @Check(ex = "idCard != null", msg = "ID number cannot be empty")
    @Check(ex = "idCard =~ /^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])||(1[0-2]))((0[1-9])||(1\\d)||(2\\d)||(3[0-1]))\\d{3}([0-9]||X)$/", msg = "ID number format is incorrect")
    @Check(ex = "gender == 1", msg = "sex")
    @Check(ex = "date =~ /^[1-9][0-9]{3}-((0)[1-9]|(1)[0-2])-((0)[1-9]|[1,2][0-9]|(3)[0,1])$/", msg = "Wrong date format")
    @Check(ex = "date > '2019-12-20 00:00:00:00'", msg = "The date must be greater than 2019-12-20")
    public HttpResult simple(String name, Integer age, String phone, String idCard, String date) {
        System.out.println("name = " + name);
        System.out.println("age = " + age);
        System.out.println("phone = " + phone);
        System.out.println("idCard = " + idCard);
        System.out.println("date = " + date);
        return HttpResult.success();
    }
}