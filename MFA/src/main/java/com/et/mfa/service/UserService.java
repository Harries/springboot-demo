package com.et.mfa.service;

import com.et.mfa.domain.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private static final List<User> users = new ArrayList<>();
    private static final int SECRET_SIZE = 10;

    @Value("${2fa.enabled}")
    private boolean isTwoFaEnabled;

    public User register(String login, String password) {
        User user = new User(login, password, generateSecret());
        users.add(user);

        return user;
    }

    public Optional<User> findUser(String login, String password) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }

    private String generateSecret() {
        return RandomStringUtils.random(SECRET_SIZE, true, true).toUpperCase();
    }
}