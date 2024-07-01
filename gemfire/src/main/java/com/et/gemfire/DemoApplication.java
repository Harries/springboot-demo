package com.et.gemfire;

import com.et.gemfire.entity.People;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.cache.config.EnableGemfireCaching;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;

@SpringBootApplication
@EnableEntityDefinedRegions(basePackageClasses = People.class, clientRegionShortcut = ClientRegionShortcut.PROXY ) // 只是当代理转发的操作
@EnablePdx
@EnableCachingDefinedRegions
@EnableGemfireCaching
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
