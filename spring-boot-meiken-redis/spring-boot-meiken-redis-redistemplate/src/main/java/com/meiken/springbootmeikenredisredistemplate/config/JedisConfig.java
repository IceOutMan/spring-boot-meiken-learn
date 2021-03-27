package com.meiken.springbootmeikenredisredistemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author glf
 * @Date 2020/11/4
 */
@Configuration
public class JedisConfig {

//    @Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
//        redisConfiguration.setDatabase(0);
//        return new JedisConnectionFactory(redisConfiguration);
//    }
//
//    @Bean
//    public RedisTemplate redisTemplate() {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        return redisTemplate;
//    }

}
