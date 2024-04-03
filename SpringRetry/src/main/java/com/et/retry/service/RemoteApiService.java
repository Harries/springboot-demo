package com.et.retry.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class RemoteApiService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Retryable(value = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5))
    public boolean pay(int num) throws Exception{
        logger.info("invoke third method");
        logger.info("do something... {}", LocalDateTime.now());
        //mock exception
        if(num==0) {
            throw new Exception("error，need retry");
        }
        return true;

    }
    @Recover
    public boolean recover(int num) throws Exception {
        logger.info("recover ... {},{}", num, LocalDateTime.now());
        return false;
    }

}