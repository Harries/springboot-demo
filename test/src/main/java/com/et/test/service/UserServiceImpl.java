package com.et.test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author liuhaihua
 * @Date 2024/4/4 13:16
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService{
	@Value("${server.username}")
	private String username;
	@Override
	public String getUserName() {
		return username;
	}
}
