package com.et.quartz.config;

import com.et.quartz.job.SimpleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class JobInit implements ApplicationRunner {

    private static final String ID = "SUMMERDAY";

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity(ID + " 02")
                .storeDurably()
                .build();
        CronScheduleBuilder scheduleBuilder =
                CronScheduleBuilder.cronSchedule("0/10 * * * * ? *");
        // 创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(ID + " 02Trigger")
                .withSchedule(scheduleBuilder)
                .startNow() //立即執行一次任務
                .build();
        Set<Trigger> set = new HashSet<>();
        set.add(trigger);
        // boolean replace 表示启动时对数据库中的quartz的任务进行覆盖。
        scheduler.scheduleJob(jobDetail, set, true);
    }
}

