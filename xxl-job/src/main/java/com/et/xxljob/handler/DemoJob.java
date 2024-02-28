package com.et.xxljob.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoJob {
    /**
     * 简单的job，调度器
     */
    @XxlJob("job1")
    public void job1() {
        log.info("do job1");
    }
}

