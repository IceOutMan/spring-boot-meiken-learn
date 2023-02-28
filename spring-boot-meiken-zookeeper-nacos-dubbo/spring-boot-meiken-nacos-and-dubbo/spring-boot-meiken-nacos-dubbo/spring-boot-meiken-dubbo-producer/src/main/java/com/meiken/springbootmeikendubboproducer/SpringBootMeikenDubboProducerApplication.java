package com.meiken.springbootmeikendubboproducer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
// @NacosPropertySource(dataId = "spring-boot-meiken-dubbo-producer", autoRefreshed = true)
// @EnableNacosConfig
@SpringBootApplication
public class SpringBootMeikenDubboProducerApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootMeikenDubboProducerApplication.class, args);
    }

}
