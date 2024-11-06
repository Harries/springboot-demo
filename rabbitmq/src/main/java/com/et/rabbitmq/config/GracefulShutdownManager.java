package com.et.rabbitmq.config;

import com.et.rabbitmq.filter.GracefulShutdownFilter;
import com.et.rabbitmq.resources.MQManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GracefulShutdownManager implements DisposableBean {

    @Autowired
    private MQManager mqManager;



    @Autowired
    private GracefulShutdownFilter shutdownFilter;

    @Override
    public void destroy() throws Exception {
        // reject requests
        shutdownFilter.startShutdown();

        // unsubscribe MQ
        mqManager.unsubscribeFromMQ();

        // Dubbo service offline
        //dubboManager.unregisterDubboServices();

        // Nacos service offline
       // nacosManager.deregisterFromNacos();

        // wait all task finish
        waitForTasksToComplete();
    }

    private void waitForTasksToComplete() {
        System.out.println("Waiting for tasks to complete...");
        // use CountDownLatch or other
    }
}