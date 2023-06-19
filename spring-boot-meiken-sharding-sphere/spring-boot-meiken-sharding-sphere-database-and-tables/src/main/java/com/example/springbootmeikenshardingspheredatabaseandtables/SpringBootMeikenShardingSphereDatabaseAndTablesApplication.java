package com.example.springbootmeikenshardingspheredatabaseandtables;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.springbootmeikenshardingspheredatabaseandtables.dao")
@SpringBootApplication
public class SpringBootMeikenShardingSphereDatabaseAndTablesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMeikenShardingSphereDatabaseAndTablesApplication.class, args);
    }

}
