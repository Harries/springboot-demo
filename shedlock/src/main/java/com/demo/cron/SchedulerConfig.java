package com.demo.cron;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class SchedulerConfig {

    @Scheduled(cron = "0 0/1 * * * ?")
    @SchedulerLock(name = "scheduledTaskName", lockAtMostFor = "PT10M", lockAtLeastFor = "PT0M")
    public void scheduledTask() {
        //  some logic code
        System.out.println("Executing scheduled task");
    }

}