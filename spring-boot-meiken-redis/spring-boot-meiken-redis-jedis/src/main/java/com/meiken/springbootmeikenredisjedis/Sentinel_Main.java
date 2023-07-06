package com.meiken.springbootmeikenredisjedis;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class Sentinel_Main {
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        String masterName = "myMaster";
        Set<String> sentinels = new HashSet<>();
        sentinels.add(new HostAndPort("192.168.65.60",26380).toString());
        sentinels.add(new HostAndPort("192.168.65.60",26381).toString());
        sentinels.add(new HostAndPort("192.168.65.60",26382).toString());

        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, config, 3000);
        Jedis jedis = jedisSentinelPool.getResource();
        // do something

    }
}
