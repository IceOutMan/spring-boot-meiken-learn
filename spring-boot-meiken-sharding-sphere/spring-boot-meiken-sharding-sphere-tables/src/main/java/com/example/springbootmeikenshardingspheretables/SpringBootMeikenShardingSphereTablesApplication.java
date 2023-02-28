package com.example.springbootmeikenshardingspheretables;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.springbootmeikenshardingspheretables.dao")
@SpringBootApplication
public class SpringBootMeikenShardingSphereTablesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMeikenShardingSphereTablesApplication.class, args);
    }

}
