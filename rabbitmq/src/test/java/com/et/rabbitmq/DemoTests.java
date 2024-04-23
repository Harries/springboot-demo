package com.et.rabbitmq;

import com.et.rabbitmq.sender.HelloSender;
import com.et.rabbitmq.sender.TopicSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoTests {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private HelloSender helloSender;
    @Autowired
    TopicSender topicSender;
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
    public void hello() throws Exception {
        helloSender.send();
        Thread.sleep(50000);
    }
    @Test
    public void topicOne() throws Exception {
        topicSender.send_one();
        Thread.sleep(50000);
    }
    @Test
    public void topicTwo() throws Exception {
        topicSender.send_two();
        Thread.sleep(50000);
    }
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Test
    public void sendFanout() throws InterruptedException {
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
        Thread.sleep(50000);
    }
}

