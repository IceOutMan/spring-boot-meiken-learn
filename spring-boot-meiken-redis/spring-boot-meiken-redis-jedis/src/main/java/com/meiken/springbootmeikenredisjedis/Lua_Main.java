package com.meiken.springbootmeikenredisjedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;


public class Lua_Main {

    public static void main(String[] args) {
//        lua();
        lua_exception();
    }

    public static void lua() {
        JedisPool jedisPool = new JedisPool("localhost", 6379);

        Jedis jedis = jedisPool.getResource();
        // 初始化库存
        jedis.set("product_stock_9527", "15");
        try {
            String script = "local count = redis.call('get', KEYS[1])" +
                    "local a = tonumber(count) " +
                    "local b = tonumber(ARGV[1]) " +
                    "if a >=b then " +
                    "   redis.call('set',KEYS[1], a-b) " +
                    "   return 1 " +
                    "end " +
                    "return 0 ";

            Object result = jedis.eval(script, Arrays.asList("product_stock_9527"), Arrays.asList("10"));
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void lua_exception() {
        JedisPool jedisPool = new JedisPool("localhost", 6379);

        Jedis jedis = jedisPool.getResource();
        // 初始化库存
        jedis.set("product_stock_9527", "15");
        try {
            String script = "local count = redis.call('get', KEYS[1])" +
                    "local a = tonumber(count) " +
                    "local b = tonumber(ARGV[1]) " +
                    "if a >=b then " +
                    "   redis.call('set',KEYS[1], a-b) " +
                    "   b == 0 " +
                    "   return 1 " +
                    "end " +
                    "return 0 ";

            Object result = jedis.eval(script, Arrays.asList("product_stock_9527"), Arrays.asList("10"));
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

