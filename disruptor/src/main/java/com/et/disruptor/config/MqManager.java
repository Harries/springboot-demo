package com.et.disruptor.config;

import com.et.disruptor.event.HelloEventFactory;
import com.et.disruptor.event.HelloEventHandler;
import com.et.disruptor.model.MessageModel;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class MqManager {
    private static Disruptor<MessageModel> disruptor;
    @Bean("ringBuffer")
    public RingBuffer<MessageModel> messageModelRingBuffer() {
        //define the thread pool for consumer message hander， Disruptor touch the consumer event to process by java.util.concurrent.ExecutorSerivce
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //define Event Factory
        HelloEventFactory factory = new HelloEventFactory();
        //ringbuffer byte size
        int bufferSize = 1024 * 256;
        //单线程模式，获取额外的性能
        disruptor = new Disruptor<>(factory, bufferSize, executor, ProducerType.SINGLE, new BlockingWaitStrategy());
        //set consumer event
        disruptor.handleEventsWith(new HelloEventHandler());
        //start disruptor thread
        disruptor.start();
        //gain ringbuffer ring，to product event
        RingBuffer<MessageModel> ringBuffer = disruptor.getRingBuffer();
        return ringBuffer;
    }
    //@PreDestroy
    public void shutdownDisruptor() {
        if (disruptor != null) {
            System.out.println("close Disruptor...");
            disruptor.shutdown(); //cl0se Disruptor
        }
    }
}
