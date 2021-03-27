package com.meiken.springbootmeikenredisredisson.controller;

import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @Author glf
 * @Date 2020/11/5
 */
@RestController
@RequestMapping("/redis_redisson")
public class TestController {

    @Autowired
    private RedissonClient redissonClient;

    @PostMapping("/get")
    @ResponseBody
    public Object get(@RequestParam("key") String key) {

        RBucket<Object> bucket = redissonClient.getBucket(key);
        Object value = bucket.get();

        return value;
    }

    @PostMapping("/set")
    public void set(@RequestParam("key") String key, @RequestParam("value") String value) {


        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value);

        bucket.expire(10, TimeUnit.SECONDS);

    }

    @GetMapping("/lock")
    public void lock() {

        RLock lock = redissonClient.getLock("LOCK_TEST");
        Boolean lockFlag = false;

        try {
            lockFlag = lock.tryLock(0,TimeUnit.SECONDS);
            if (lockFlag) {
                Thread.sleep(40000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("FINALLY UNLOCK");
            lock.unlock();
        }


    }

    @GetMapping("/lock2")
    public void lock2() {

        RLock lock = redissonClient.getLock("LOCK_TEST");
        Boolean lockFlag = false;

        try {
            lockFlag = lock.tryLock(0,TimeUnit.SECONDS);
            if (lockFlag) {
                Thread.sleep(40000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("FINALLY UNLOCK");
            lock.unlock();
        }


    }


}
