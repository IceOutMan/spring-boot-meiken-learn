package com.meiken.springbootmeikenredisredistemplate.controller;

import com.meiken.springbootmeikenredisredistemplate.entity.Address;
import com.meiken.springbootmeikenredisredistemplate.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @Author glf
 * @Date 2020/11/4
 */
@RestController
@RequestMapping("/redis_lettuce")
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    @Resource(name = "redisTemplate")
    private HashOperations hashOps;

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOps;

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, String> zSetOps;


    @PostMapping("/get")
    @ResponseBody
    public Object get(@RequestParam("key") String key) {
//        Object value = listOps.leftPop(key);

        HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();
        Map<byte[], byte[]> loadedHash = hashOps.entries(key);


        Object value = (Person) mapper.fromHash(loadedHash);

        redisTemplate.delete(key);


        return value;
    }

    @PostMapping("/set")
    public void set(@RequestParam("key") String key, @RequestParam("value") String value) {

//        listOps.leftPush(key,value);

        Address address = new Address();
        address.setCity("上海");
        address.setDetail("上海市浦东新区");


        Person person = new Person();
        person.setName("张三");
        person.setAge(12);
        person.setAddress(address);
        person.setDate(new Date());

        HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();

        Map<byte[], byte[]> map = mapper.toHash(person);

        hashOps.putAll(key, map);

//        redisTemplate.expire(key,10,TimeUnit.SECONDS);


    }
}
