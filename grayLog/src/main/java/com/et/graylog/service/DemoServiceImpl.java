package com.et.graylog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName DemoServiceImpl
 * @Description todo
 * @date 2024年01月30日 13:58
 */
@Slf4j
@Service
public class DemoServiceImpl implements  DemoService{
    @Override
    public void hello() {
        log.info("this is a test service,hello");
    }
}
