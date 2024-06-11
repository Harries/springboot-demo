package com.et.vavr;

import io.vavr.Function4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoTests {
    private Logger log = LoggerFactory.getLogger(getClass());


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
    public void multiFunctionTest() {
        Function4<String, String, Boolean, Integer, String> func =
                (country, name, isMan, score) -> String.format("%s-%s-%s-%d", country, name, isMan ? "男" : "女", score);
        System.out.println(func.apply("中国", "小明", true, 10));
    }
}

