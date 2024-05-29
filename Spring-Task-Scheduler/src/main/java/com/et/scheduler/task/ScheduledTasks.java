package com.et.scheduler.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ScheduledTasks {

    @Scheduled(fixedRate = 5000)
    public void performTask() {
        log.info("Task performed at {}", LocalDateTime.now());
    }

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void performTask1() {
        log.info("Task performed at {}", LocalDateTime.now());
    }

    /**
     *  ┌───────────── second (0-59)
     *  │ ┌───────────── minute (0 - 59)
     *  │ │ ┌───────────── hour (0 - 23)
     *  │ │ │ ┌───────────── day of the month (1 - 31)
     *  │ │ │ │ ┌───────────── month (1 - 12) (or JAN-DEC)
     *  │ │ │ │ │ ┌───────────── day of the week (0 - 7)
     *  │ │ │ │ │ │          (0 or 7 is Sunday, or MON-SUN)
     *  │ │ │ │ │ │
     *  * * * * * *
     *
     */
    @Scheduled(cron="*/5 * * * * MON-FRI")
    public void doSomething() {
        log.info("cron ....");
    }
}