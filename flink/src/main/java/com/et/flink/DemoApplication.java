package com.et.flink;

import com.et.flink.service.FlinkJobService;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		// 获取 FlinkJobService Bean，并运行 Flink 作业
		FlinkJobService flinkJobService = context.getBean(FlinkJobService.class);
		try {
			flinkJobService.runFlinkJob();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
}
