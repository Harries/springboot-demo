package com.et.sftp.Interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * @author : qiushicai
 * @date : Created in 2020/11/20
 * @description :
 */

@Component
public class MyChannelInterceptor implements ChannelInterceptor {


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("----------------------------preSend------------------------------");
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        System.out.println("----------------------------postReceive------------------------------");
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {

    }
}
