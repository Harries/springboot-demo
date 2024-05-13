package com.et.provider.service;

import com.et.api.UserService;
import com.et.api.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @service import package is org.apache.dubbo.config.annotation.Service
 */
@Service
@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public String getUserInfo() {
        log.info("this is a test");
        return "userTest";
    }

    @Override
    public User getUserById(String userId) {
        log.info("invoke getUserById method");
        User user = new User();
        user.setUserId(Long.valueOf(userId));
        user.setUserInfo("test");
        user.setUserName("lin");
        return user;
    }

}
