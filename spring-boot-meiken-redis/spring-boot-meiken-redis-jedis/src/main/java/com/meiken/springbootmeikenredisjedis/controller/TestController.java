package com.meiken.springbootmeikenredisjedis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @Author glf
 * @Date 2020/11/4
 */
@RestController
@RequestMapping("/redis_jedis")
public class TestController {
    @Autowired
    private JedisConnectionFactory redisConnectionFactory;

    @PostMapping("/get")
    @ResponseBody
    public Object get(@RequestParam("key") String key) {

        RedisConnection connection = redisConnectionFactory.getConnection();
        Object value = connection.get(key.getBytes());

        return value;
    }

    @PostMapping("/set")
    public void set(@RequestParam("key") String key, @RequestParam("value") String value) {


        RedisConnection connection = redisConnectionFactory.getConnection();

        connection.set(key.getBytes(), value.getBytes());

        connection.expire(key.getBytes(), 10);

    }
}

