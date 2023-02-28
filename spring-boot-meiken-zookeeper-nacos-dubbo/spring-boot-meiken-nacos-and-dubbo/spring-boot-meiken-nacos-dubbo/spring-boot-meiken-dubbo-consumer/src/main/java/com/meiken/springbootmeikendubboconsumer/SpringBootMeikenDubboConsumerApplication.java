package com.meiken.springbootmeikendubboconsumer;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@NacosPropertySource(dataId = "spring-boot-meiken-dubbo-consumer", autoRefreshed = true)
@EnableDubbo(scanBasePackages = "com.meiken")
@SpringBootApplication
public class SpringBootMeikenDubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMeikenDubboConsumerApplication.class, args);
    }

}
