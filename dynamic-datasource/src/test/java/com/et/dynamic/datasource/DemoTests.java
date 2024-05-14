package com.et.dynamic.datasource;

import com.et.dynamic.datasource.model.entity.UserInfo;
import com.et.dynamic.datasource.service.userinfo.UserInfoService;
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
    UserInfoService userInfoService;
    @Before
    public void before()  {
        log.info("init some data");
    }
    @After
    public void after(){
        log.info("clean some data");
    }
    @Test
    public void execute()  {
        log.info("your method test Code");
    }
    @Test
    public void query()  {
        log.info("your method test Code");
        userInfoService.lambdaQuery().list().forEach(System.out::println);
    }
    @Test
    public void testQueryWrapper()  {
        log.info("your method test Code");
        userInfoService.testQueryWrapper(3).forEach(System.out::println);
    }

    @Test
    public void insert()  {
        log.info("your method test Code");

        for(int i =1;i<10;i++) {
            UserInfo ui =  new UserInfo();
            ui.setUserId(i+"id");
            userInfoService.removeById(i+"id");
            ui.setUsername("HBLOG"+i);
            ui.setAge(i);
            userInfoService.save(ui);
        }
    }

}

