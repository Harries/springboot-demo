package com.et59.rocketmq.util;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * RocketMq助手
 *
 * @Author: heyuhua
 * @Date: 2020/1/8 10:03
 */
@Component
public class RocketMqHelper {

    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(RocketMqHelper.class);

    /**
     * rocketmq模板注入
     */
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostConstruct
    public void init() {
        LOG.info("---RocketMq助手初始化---");
    }

    /**
     * 发送异步消息
     *
     * @param topic   消息Topic
     * @param message 消息实体
     */
    public void asyncSend(Enum topic, Message<?> message) {
        asyncSend(topic.name(), message, getDefaultSendCallBack());
    }


    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     */
    public void asyncSend(Enum topic, Message<?> message, SendCallback sendCallback) {
        asyncSend(topic.name(), message, sendCallback);
    }

    /**
     * 发送异步消息
     *
     * @param topic   消息Topic
     * @param message 消息实体
     */
    public void asyncSend(String topic, Message<?> message) {
        rocketMQTemplate.asyncSend(topic, message, getDefaultSendCallBack());
    }

    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     */
    public void asyncSend(String topic, Message<?> message, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(topic, message, sendCallback);
    }

    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     * @param timeout      超时时间
     */
    public void asyncSend(String topic, Message<?> message, SendCallback sendCallback, long timeout) {
        rocketMQTemplate.asyncSend(topic, message, sendCallback, timeout);
    }

    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     * @param timeout      超时时间
     * @param delayLevel   延迟消息的级别
     */
    public void asyncSend(String topic, Message<?> message, SendCallback sendCallback, long timeout, int delayLevel) {
        rocketMQTemplate.asyncSend(topic, message, sendCallback, timeout, delayLevel);
    }

    /**
     * 发送顺序消息
     *
     * @param message
     * @param topic
     * @param hashKey
     */
    public void syncSendOrderly(Enum topic, Message<?> message, String hashKey) {
        syncSendOrderly(topic.name(), message, hashKey);
    }


    /**
     * 发送顺序消息
     *
     * @param message
     * @param topic
     * @param hashKey
     */
    public void syncSendOrderly(String topic, Message<?> message, String hashKey) {
        LOG.info("发送顺序消息，topic:" + topic + ",hashKey:" + hashKey);
        rocketMQTemplate.syncSendOrderly(topic, message, hashKey);
    }

    /**
     * 发送顺序消息
     *
     * @param message
     * @param topic
     * @param hashKey
     * @param timeout
     */
    public void syncSendOrderly(String topic, Message<?> message, String hashKey, long timeout) {
        LOG.info("发送顺序消息，topic:" + topic + ",hashKey:" + hashKey + ",timeout:" + timeout);
        rocketMQTemplate.syncSendOrderly(topic, message, hashKey, timeout);
    }

    /**
     * 默认CallBack函数
     *
     * @return
     */
    private SendCallback getDefaultSendCallBack() {
        return new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOG.info("---发送MQ成功---");
            }

            @Override
            public void onException(Throwable throwable) {
                throwable.printStackTrace();
                LOG.error("---发送MQ失败---"+throwable.getMessage(), throwable.getMessage());
            }
        };
    }


    @PreDestroy
    public void destroy() {
        LOG.info("---RocketMq助手注销---");
    }

}




