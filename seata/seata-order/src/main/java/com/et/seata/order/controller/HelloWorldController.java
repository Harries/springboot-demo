package com.et.seata.order.controller;

import com.et.seata.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/hello")
    public Map<String, Object> showHelloWorld(@RequestParam("userId") Long userId,
                                              @RequestParam("productId") Long productId,
                                              @RequestParam("price") Integer price) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        map.put("reuslt", orderService.createOrder(userId,productId,price));
        return map;
    }
}