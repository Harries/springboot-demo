package com.et.twilio.controller;

import com.et.twilio.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        try {
            emailService.sendEmail(to, subject, body);
            return "Email sent successfully!";
        } catch (IOException e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}