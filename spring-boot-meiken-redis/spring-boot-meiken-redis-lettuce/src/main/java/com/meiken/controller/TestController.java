package com.meiken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @Author glf
 * @Date 2020/11/4
 */
@RestController
@RequestMapping("/redis_lettuce")
public class TestController {

    @Autowired
    private LettuceConnectionFactory redisConnectionFactory;

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
