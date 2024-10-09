package com.et.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class RedissonConfig {

    @Value("${redisson.redis.address}")
    private String address;

    @Value("${redisson.redis.password}")
    private String password;

    @Bean
    public Config redissionConfig() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(address);
        if (!StringUtils.isEmpty(password)) {
            singleServerConfig.setPassword(password);
        }

        return config;
    }

    @Bean
    public RedissonClient redissonClient() {
        return Redisson.create(redissionConfig());
    }
}