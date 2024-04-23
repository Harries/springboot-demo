package com.et.quartz;

import com.et.quartz.job.SimpleJob;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoTests {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private Scheduler scheduler;

    @Before
    public void before()  {
        log.info("init some data");
    }
    @After
    public void after(){
        log.info("clean some data");
    }
    @Test
    public void execute() throws SchedulerException, InterruptedException {
        log.info("your method test Code");
        JobDetail simpleJob = JobBuilder.newJob(SimpleJob.class)        //传入一个Job类
                .withIdentity("SimpleJob", "HBLOGTriggers")    //(name, group)标识唯一一个JobDetail
                .storeDurably()        //在没有Trigger关联的情况下保存该任务到调度器
                .build();
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(5)      //每5秒执行一次
                .repeatForever();              //无限循环执行
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("SimpleTrigger", "HBLOGTriggers")    //(name, group)唯一标识一个Trigger
                .startNow()
                .withSchedule(scheduleBuilder)
                .build();

        scheduler.scheduleJob(simpleJob, simpleTrigger);
        Thread.sleep(300000);

        /*CronScheduleBuilder scheduleBuilder02 = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");    //Cron表达式，每5秒执行一次
        CronTrigger cronTrigger = (CronTrigger) TriggerBuilder.newTrigger()
                .withIdentity("CronJob", "HBLOGTriggers")    //(name, group)唯一标识一个Trigger
                .startNow()                         //调用scheduler.scheduleJob()后立即开始执行定时任务
                .withSchedule(scheduleBuilder02)      //不同的scheduleBuilder
                .build();
        scheduler.scheduleJob(simpleJob, cronTrigger);*/
    }

}

