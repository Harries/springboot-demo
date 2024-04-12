package com.et.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        // method one
        SpringApplication.run(SpringbootDemoApplication.class, args);

        // method two
//        SpringApplicationBuilder builder = new SpringApplicationBuilder()
//                .web(WebApplicationType.REACTIVE).sources(SpringbootDemoApplication.class);
//        builder.run(args);
    }

}
