package com.meiken.springbootmeikendubboproducer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.spring.context.config.ConfigurationBeanCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@NacosPropertySource(dataId = "spring-boot-meiken-dubbo-producer",autoRefreshed = true)
@EnableNacosConfig
@SpringBootApplication
public class SpringBootMeikenDubboProducerApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootMeikenDubboProducerApplication.class, args);
    }

}
