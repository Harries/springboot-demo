package com.et.twilio.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SmsService {

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    private Map<String, String> verificationCodes = new HashMap<>();

    public void sendVerificationCode(String toPhoneNumber) {
        String code = generateVerificationCode();
        verificationCodes.put(toPhoneNumber, code);

        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                "Your verification code is: " + code)
                .create();
    }

    public boolean verifyCode(String phoneNumber, String code) {
        String storedCode = verificationCodes.get(phoneNumber);
        return storedCode != null && storedCode.equals(code);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // 生成6位验证码
    }
}