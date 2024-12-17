package com.et;

import com.et.filter.StaticResourceFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean<StaticResourceFilter> staticResourceFilter() {
		FilterRegistrationBean<StaticResourceFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new StaticResourceFilter());
		registrationBean.addUrlPatterns("/images/*");
		return registrationBean;
	}
}
