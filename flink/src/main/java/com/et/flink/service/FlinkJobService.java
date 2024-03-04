package com.et.flink.service;

import com.et.flink.job.StringProcessingJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlinkJobService {

    @Autowired
    private StringProcessingJob stringProcessingJob;

    public void runFlinkJob() throws Exception {
        stringProcessingJob.main(new String[]{});
    }
}
