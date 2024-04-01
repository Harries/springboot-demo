package com.et.jwt.service;


import com.et.jwt.entity.User;
import com.et.jwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2020-05-26
 */

@Service("UserService")
public class UserService {
    @Autowired
    UserMapper userMapper;
    public User findByUsername(User user){
        return userMapper.findByUsername(user.getUsername());
    }
    public User findUserById(String userId) {
        return userMapper.findUserById(userId);
    }
}
