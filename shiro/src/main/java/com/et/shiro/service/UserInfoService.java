package com.et.shiro.service;

import com.et.shiro.entity.UserInfo;

public interface UserInfoService {

    public UserInfo findByUsername(String username);
}