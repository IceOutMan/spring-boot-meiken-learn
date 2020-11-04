package com.meiken.springbootmeikendubboproducer.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author glf
 * @Date 2020/9/4
 */
@RestController
@RequestMapping("/producer")
public class ProducerController {

    @NacosValue(value = "${dubbo.application.name}")
    private String applicationName;

    @RequestMapping("/test")
    public String test(){
        return applicationName;
    }
}
