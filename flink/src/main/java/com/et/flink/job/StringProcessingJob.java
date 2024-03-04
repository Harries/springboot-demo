package com.et.flink.job;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.stereotype.Component;

// Flink作业
@Component
public class StringProcessingJob {

    public static void main(String[] args) throws Exception {

        // 初始化执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 添加数据源
        DataStream<String> text = env.fromElements("Hello", "Flink", "Spring Boot");

        // 数据处理
        DataStream<String> processedText = text
                .map(new MapFunction<String, String>() {
                    @Override
                    public String map(String value) throws Exception {
                        return "Processed: " + value;
                    }
                });

        // 输出结果
        processedText.print();

        // 执行作业
        env.execute("String Processing Job");

    }
}
