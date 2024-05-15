package com.et.shiro.dao;

import com.et.shiro.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoDao extends CrudRepository<UserInfo,Long> {

    public UserInfo findByUsername(String username);
}