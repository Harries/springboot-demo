package com.et.rabbitmq.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HelloReceiver {


    @RabbitListener(queues = "hello")
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }

    @RabbitListener(queues = {"topic.one"})
    public void receiveTopic1(@Payload String fileBody) {
        log.info("topic1：" + fileBody);
    }

    @RabbitListener(queues = {"topic.two"})
    public void receiveTopic2(@Payload String fileBody) {
        log.info("topic2：" + fileBody);
    }
    @RabbitListener(queues = {"fanout.A"})
    public void fanoutA(@Payload String fileBody) {
        log.info("fanoutA：" + fileBody);
    }

    @RabbitListener(queues = {"fanout.B"})
    public void fanoutB(@Payload String fileBody) {
        log.info("fanoutB：" + fileBody);
    }
    @RabbitListener(queues = {"fanout.C"})
    public void fanoutC(@Payload String fileBody) {
        log.info("fanoutC：" + fileBody);
    }
}