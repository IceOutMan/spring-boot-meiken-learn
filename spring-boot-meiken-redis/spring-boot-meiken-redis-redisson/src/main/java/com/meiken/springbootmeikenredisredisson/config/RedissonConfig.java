package com.meiken.springbootmeikenredisredisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author glf
 * @Date 2020/11/5
 */
@Configuration
public class RedissonConfig {


    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://localhost:6379");
        singleServerConfig.setDatabase(3);


        RedissonClient redissonClient = Redisson.create(config);

        return redissonClient;
    }
}
