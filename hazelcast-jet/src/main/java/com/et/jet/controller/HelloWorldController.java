package com.et.jet.controller;

import com.et.jet.DemoApplication;
import com.et.jet.example.WordCount;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.test.TestSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @Autowired
    JetInstance instance;
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
    @RequestMapping("/submitJob")
    public void submitJob() {
        Pipeline pipeline = Pipeline.create();
        pipeline.readFrom(TestSources.items("foo", "bar"))
                .writeTo(Sinks.logger());

        JobConfig jobConfig = new JobConfig()
                .addClass(HelloWorldController.class);
        instance.newJob(pipeline, jobConfig).join();
    }
    @Autowired
    WordCount wordCount;
    @RequestMapping("/wordCount")
    public void wordCount() {
        wordCount.go();
    }

}