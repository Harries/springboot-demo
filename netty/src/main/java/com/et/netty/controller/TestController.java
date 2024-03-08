package com.et.netty.controller;

import com.et.netty.service.PushMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author dongliang7
 * @projectName
 * @ClassName TestController.java
 * @description: TODO
 * @createTime 2023年02月06日 17:48:00
 */
@RestController
@RequestMapping("/push")
public class TestController {
    @Autowired
    PushMsgService pushMsgService;

    /**
     * 推送消息到具体客户端
     * @param uid
     */
    @GetMapping("/{uid}")
    public void pushOne(@PathVariable String uid) {
        pushMsgService.pushMsgToOne(uid, "hello-------------------------");
    }

    /**
     * 推送消息到所有客户端
     */
    @GetMapping("/pushAll")
    public void pushAll() {
        pushMsgService.pushMsgToAll("hello all-------------------------");
    }
}