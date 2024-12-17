package com.et.controller;

import com.et.filter.TimeValidator;
import com.et.filter.TokenValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ResourceController {

    @GetMapping("/resources/image")
    public String getImage(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(required = false) String token,
                           @RequestParam(required = false) String timestamp) throws IOException {

        // 验证 Token
        if (!TokenValidator.validateToken(request, response)) {
            return null;
        }

        // 验证时间戳
        if (!TimeValidator.validateTimestamp(request, response)) {
            return null;
        }

        return "Access granted to image resource";
    }
}