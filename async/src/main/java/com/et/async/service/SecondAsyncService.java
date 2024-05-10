package com.et.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName SecondAsyncService
 * @Description todo
 * @date 2024年05月10日 16:24
 */
@Service
@Slf4j
public class SecondAsyncService {
    @Async
    public CompletableFuture<String> asyncGetData() throws InterruptedException {
        log.info("Execute method asynchronously " + Thread.currentThread()
                .getName());
        Thread.sleep(4000);
        return new AsyncResult<>(super.getClass().getSimpleName() + " response !!! ").completable();
    }
}
