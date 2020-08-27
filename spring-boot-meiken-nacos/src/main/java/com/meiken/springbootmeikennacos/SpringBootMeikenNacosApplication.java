package com.meiken.springbootmeikennacos;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@NacosPropertySource(dataId = "spring-boot-meiken-nacos",autoRefreshed = true)
@SpringBootApplication
public class SpringBootMeikenNacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMeikenNacosApplication.class, args);

    }

}
