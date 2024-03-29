package com.meiken.springbootmeikendatasourcetkmybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.meiken.springbootmeikendatasourcetkmybatis.dao")
public class SpringBootMeikenDatasourceTkMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMeikenDatasourceTkMybatisApplication.class, args);
    }

}
