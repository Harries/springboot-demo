package com.et.retry;

import com.et.retry.service.RemoteApiService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoTests {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    RemoteApiService remoteApiService;

    @Before
    public void before()  {
        log.info("init some data");
    }
    @After
    public void after(){
        log.info("clean some data");
    }
    @Test
    public void execute() throws Exception {
        log.info("pay result:"+remoteApiService.pay(0));
    }

}

