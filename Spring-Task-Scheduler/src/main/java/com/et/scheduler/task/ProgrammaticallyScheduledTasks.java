package com.et.scheduler.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ProgrammaticallyScheduledTasks {

    private final TaskScheduler taskScheduler;

    @Autowired
    public ProgrammaticallyScheduledTasks(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
        scheduleWithFixedDelay();
        scheduleAtFixedRate();
        scheduleCronTrigger();
        scheduleperiodicTrigger();
    }

    public void scheduleWithFixedDelay() {
        taskScheduler.scheduleWithFixedDelay(new RunnableTask("Fixed 1 second Delay"), 1000);
    }
    public void scheduleAtFixedRate() {
        taskScheduler.scheduleAtFixedRate(new RunnableTask("Fixed Rate of 2 seconds") , 2000);
    }
    public void scheduleCronTrigger() {
        CronTrigger cronTrigger = new CronTrigger("10 * * * * ?");
        taskScheduler.schedule(new RunnableTask("Cron Trigger"), cronTrigger);
    }
    public void scheduleperiodicTrigger() {
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(2000000, TimeUnit.MICROSECONDS);
        taskScheduler.schedule(new RunnableTask("Periodic Trigger"), periodicTrigger);
    }
}