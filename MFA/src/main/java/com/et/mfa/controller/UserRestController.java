package com.et.mfa.controller;

import com.et.mfa.domain.User;

import com.et.mfa.service.UserService;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user/", method = RequestMethod.POST)
public class UserRestController {
    @Value("${2fa.enabled}")
    private boolean isTwoFaEnabled;
    @Autowired
    private UserService userService;

    @PostMapping("/register/{login}/{password}")
    public String register(@PathVariable String login, @PathVariable String password) {
        User user = userService.register(login, password);
        String encodedSecret = new Base32().encodeToString(user.getSecret().getBytes());

        // This Base32 encode may usually return a string with padding characters - '='.
        // QR generator which is user (zxing) does not recognize strings containing symbols other than alphanumeric
        // So just remove these redundant '=' padding symbols from resulting string
        return encodedSecret.replace("=", "");
    }
}