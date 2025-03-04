package com.et.twilio.service;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {

    @Value("${twilio.verify-service-sid}")
    private String verifyServiceSid;

    public void sendVerificationCode(String toPhoneNumber) {
        Verification verification = Verification.creator(
                verifyServiceSid,
                toPhoneNumber,
                "sms")
                .create();
    }

    public boolean verifyCode(String toPhoneNumber, String code) {
        VerificationCheck verificationCheck = VerificationCheck.creator(
                verifyServiceSid,
                code)
                .setTo(toPhoneNumber)
                .create();

        return verificationCheck.getStatus().equals("approved");
    }
}