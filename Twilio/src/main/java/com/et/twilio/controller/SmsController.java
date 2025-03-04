package com.et.twilio.controller;

import com.et.twilio.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public String sendVerificationCode(@RequestParam String phoneNumber) {
        smsService.sendVerificationCode(phoneNumber);
        return "Verification code sent!";
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestParam String phoneNumber, @RequestParam String code) {
        boolean isValid = smsService.verifyCode(phoneNumber, code);
        return isValid ? "Verification successful!" : "Invalid verification code!";
    }
}