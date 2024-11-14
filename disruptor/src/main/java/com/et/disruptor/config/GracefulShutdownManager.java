package com.et.disruptor.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GracefulShutdownManager implements DisposableBean {




    @Autowired
    private GracefulShutdownFilter shutdownFilter;
    @Autowired
    MqManager mqManager;
    @Override
    public void destroy() throws Exception {
        // reject  new  requests
        shutdownFilter.startShutdown();

        //graceful shutdown Disruptor
        mqManager.shutdownDisruptor(); // wait all events to complete

        // wait all  your self-definite task finish
        waitForTasksToComplete();
    }

    private void waitForTasksToComplete() throws InterruptedException {
        System.out.println("Waiting for tasks to complete...");
        // use CountDownLatch or other
        //mock task process
        Thread.sleep(100000);
    }
}