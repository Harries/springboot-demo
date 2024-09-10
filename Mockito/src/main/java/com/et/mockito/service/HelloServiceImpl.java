package com.et.mockito.service;

import org.springframework.stereotype.Service;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName HelloServiceImpl
 * @Description todo
 * @date 2024/09/09/ 17:44
 */
@Service
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayhi(String name) {
        return "hi"+name;
    }
}
