package com.et.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.et.jwt.entity.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service("TokenService")

public class TokenService {
    long  outHours=1;
    public String getToken(User user) {
        Instant instant = LocalDateTime.now().plusHours(outHours).atZone(ZoneId.systemDefault()).toInstant();
        Date expire = Date.from(instant);
        String token="";
        // save information  to token ,such as: user id and Expires time
        token= JWT.create()
                .withAudience(user.getId())
                .withExpiresAt(expire)
                .sign(Algorithm.HMAC256(user.getPassword()));
        // use HMAC256 to generate token,key is user's password
        return token;
    }
}
