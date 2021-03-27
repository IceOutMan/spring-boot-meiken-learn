package com.meiken.mongolearn;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @Author glf
 * @Date 2021/2/22
 * 也可使用 spring 自动注入的方式
 */
//@Configuration
//public class MongoConfig {
//
//    @Bean
//    public MongoClient mongoClient(){
//        return MongoClients.create("mongodb://glf:glf@localhost:27017");
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate(){
//        return new MongoTemplate(mongoClient(),"developer");
//    }
//}
