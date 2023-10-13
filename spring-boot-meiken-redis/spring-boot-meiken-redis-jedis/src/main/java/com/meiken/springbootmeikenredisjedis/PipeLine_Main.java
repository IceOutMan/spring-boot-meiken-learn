package com.meiken.springbootmeikenredisjedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class PipeLine_Main {
    public static void main(String[] args) {
        pipeline();
    }

    public static void pipeline(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        JedisPool jedisPool = new JedisPool(config, "localhost", 6379);

        Jedis jedis = jedisPool.getResource();

        Pipeline pipelined = jedis.pipelined();
        try {
            for (int i = 0; i < 10; i++) {
                pipelined.incr("pipeline_key");
                pipelined.set("meiken_"+i, "iceout_man");
            }

            List<Object> objects = pipelined.syncAndReturnAll();
            System.out.println(objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
