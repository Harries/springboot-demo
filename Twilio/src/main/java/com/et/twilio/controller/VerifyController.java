package com.et.twilio.controller;

import com.et.twilio.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verify")
public class VerifyController {

    @Autowired
    private VerifyService verifyService;

    @PostMapping("/send")
    public String sendVerificationCode(@RequestParam String phoneNumber) {
        verifyService.sendVerificationCode(phoneNumber);
        return "Verification code sent!";
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestParam String phoneNumber, @RequestParam String code) {
        boolean isValid = verifyService.verifyCode(phoneNumber, code);
        return isValid ? "Verification successful!" : "Invalid verification code!";
    }
}