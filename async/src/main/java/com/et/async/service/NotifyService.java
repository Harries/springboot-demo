package com.et.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName NotifyServiceimpl
 * @Description todo
 */
@Service
@Slf4j
public class NotifyService {
    public void noAsync() {
       log.info("Execute method asynchronously. " + Thread.currentThread().getName());
    }
    public void withAsync() {
        log.info("Execute method asynchronously. " + Thread.currentThread().getName());
    }
    @Async("threadPoolTaskExecutor")
    public void mockerror() {
        int ss=12/0;
    }
    @Async
    public Future<String> asyncMethodWithReturnType() {
        log.info("Execute method asynchronously - " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
            return new AsyncResult<String>("hello world !!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Autowired
    private FirstAsyncService fisrtService;
    @Autowired
    private SecondAsyncService secondService;

    public CompletableFuture<String> asyncMergeServicesResponse() throws InterruptedException {
        CompletableFuture<String> fisrtServiceResponse = fisrtService.asyncGetData();
        CompletableFuture<String> secondServiceResponse = secondService.asyncGetData();

        // Merge responses from FirstAsyncService and SecondAsyncService
        return fisrtServiceResponse.thenCompose(fisrtServiceValue -> secondServiceResponse.thenApply(secondServiceValue -> fisrtServiceValue + secondServiceValue));
    }
}
