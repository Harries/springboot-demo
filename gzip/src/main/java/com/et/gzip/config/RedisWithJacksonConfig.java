package com.et.gzip.config;

import com.et.gzip.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisWithJacksonConfig {


    @Bean(name="redisTemplateWithJackson")
    public RedisTemplate<String, User> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {

        CompressRedis  compressRedis =  new CompressRedis();
        //redisTemplate
        RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(compressRedis);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(compressRedis);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}