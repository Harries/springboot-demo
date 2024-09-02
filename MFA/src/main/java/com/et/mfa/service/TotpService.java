package com.et.mfa.service;

import com.et.mfa.domain.TOTP;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;


@Service
public class TotpService {
    public boolean verifyCode(String totpCode, String secret) {
        String totpCodeBySecret = generateTotpBySecret(secret);

        return totpCodeBySecret.equals(totpCode);
    }

    private String generateTotpBySecret(String secret) {
        // Getting current timestamp representing 30 seconds time frame
        long timeFrame = System.currentTimeMillis() / 1000L / 30;

        // Encoding time frame value to HEX string - requred by TOTP generator which is used here.
        String timeEncoded = Long.toHexString(timeFrame);

        String totpCodeBySecret;
        try {
            // Encoding given secret string to HEX string - requred by TOTP generator which is used here.
            char[] secretEncoded = (char[]) new Hex().encode(secret);

            // Generating TOTP by given time and secret - using TOTP algorithm implementation provided by IETF.
            totpCodeBySecret = TOTP.generateTOTP(String.copyValueOf(secretEncoded), timeEncoded, "6");
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
        return totpCodeBySecret;
    }
}

