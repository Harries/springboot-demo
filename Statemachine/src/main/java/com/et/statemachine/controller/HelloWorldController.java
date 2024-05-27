package com.et.statemachine.controller;

import com.et.statemachine.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    @Resource
    private OrderService orderService;

    @RequestMapping("/testOrderStatusChange")
    public String testOrderStatusChange(){
        orderService.create();
        orderService.create();
        orderService.pay(1L);
        orderService.deliver(1L);
        orderService.receive(1L);
        orderService.pay(2L);
        orderService.deliver(2L);
        orderService.receive(2L);
        System.out.println("all orders：" + orderService.getOrders());
        return "success";
    }

}