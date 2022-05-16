package com.example.springbootmeikendatasourcemybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.example"})
public class SpringBootMeikenDatasourceMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMeikenDatasourceMybatisApplication.class, args);
    }

}
