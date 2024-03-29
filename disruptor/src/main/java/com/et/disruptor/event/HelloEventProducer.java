package com.et.disruptor.event;

import com.et.disruptor.model.MessageModel;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName HelloEventProducer
 * @Description todo
 * @date 2024年03月29日 13:26
 */
@Component
public class HelloEventProducer {
    @Autowired
    RingBuffer<MessageModel> ringBuffer;
    public synchronized void sayHelloMq(String message){
        EventTranslator<MessageModel> et = new EventTranslator<MessageModel>() {
            @Override
            public void translateTo(MessageModel messageModel, long l) {
                messageModel.setMessage(message);
            }
        };
        ringBuffer.publishEvent(et);
    }

}
