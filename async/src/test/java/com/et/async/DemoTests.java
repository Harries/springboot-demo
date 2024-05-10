package com.et.async;

import com.et.async.service.NotifyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class DemoTests {
    @Autowired
    NotifyService notifyService;

    @Before
    public void before()  {
        log.info("init some data");
    }
    @After
    public void after(){
        log.info("clean some data");
    }
    @Test
    public void execute() throws ExecutionException, InterruptedException {
        log.info("your method test Code");
        log.info("Invoking an asynchronous method. " + Thread.currentThread().getName());
        notifyService.noAsync();
        notifyService.withAsync();

    }
    @Test
    public void mockerror() throws ExecutionException, InterruptedException {
        notifyService.mockerror();
    }
    @Test
    public void testAsyncAnnotationForMethodsWithReturnType()
            throws InterruptedException, ExecutionException {
        log.info("Invoking an asynchronous method. " + Thread.currentThread().getName());
        Future<String> future = notifyService.asyncMethodWithReturnType();

        while (true) {
            if (future.isDone()) {
                log.info("Result from asynchronous process - " + future.get());
                break;
            }
            log.info("Continue doing something else. ");
            Thread.sleep(1000);
        }
    }

    @Test
    public void testAsyncAnnotationForMergedServicesResponse() throws InterruptedException, ExecutionException {
       log.info("Invoking an asynchronous method. " + Thread.currentThread().getName());
        CompletableFuture<String> completableFuture = notifyService.asyncMergeServicesResponse();

        while (true) {
            if (completableFuture.isDone()) {
               log.info("Result from asynchronous process - " + completableFuture.get());
                break;
            }
            log.info("Continue doing something else. ");
            Thread.sleep(1000);
        }
    }

}

