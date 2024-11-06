package com.et.rabbitmq.resources;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQManager {


    public void unsubscribeFromMQ() throws InterruptedException {
        // stop mq resource
        System.out.println("Stopping RabbitMQ listener container...");
        //mock
        Thread.sleep(100000);
    }
}