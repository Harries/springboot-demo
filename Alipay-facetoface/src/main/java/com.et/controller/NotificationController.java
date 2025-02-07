package com.et.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class NotificationController {

    @PostMapping("/notify")
    public String handleNotification(@RequestBody String notification) {
        // 解析通知内容并进行相应处理
        // 例如：更新订单状态等
        log.info("Received notification: {}", notification);
        return "success"; // 返回成功响应
    }
}