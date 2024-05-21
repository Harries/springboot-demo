package com.et.spel;

import com.et.spel.controller.BaseValueInject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoTests {
    private Logger log = LoggerFactory.getLogger(getClass());


    @Before
    public void before() {
        log.info("init some data");
    }

    @After
    public void after() {
        log.info("clean some data");
    }

    @Test
    public void execute() {
        log.info("your method test Code");
    }

    @Test
    public void stringSpel() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();
        log.info(message);
    }

    @Test
    public void stringSpelConcat() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        String message = (String) exp.getValue();
        log.info(message);
    }

    @Autowired
    private BaseValueInject baseValueInject;

    @Test
    public void baseValueInject() {
        System.out.println(baseValueInject.toString());

    }
}

