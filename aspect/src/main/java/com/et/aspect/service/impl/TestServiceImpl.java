package com.et.aspect.service.impl;

import com.et.aspect.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName TestServiceImpl
 * @Description todo
 * @date 2024/09/05/ 9:21
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Override
    public String sayHello(String name) {
        String  sss="hello,"+name;
        log.info(sss);
        return sss;
    }
}
