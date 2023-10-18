package com.meiken.springbootmeikennacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author glf
 * @Date 2020/8/27
 */
@RequestMapping("/nacos")
@RestController
public class TestController {

    @NacosValue(value = "${nacosTestField:isNull}", autoRefreshed = true)
    private String nacosTestField;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return nacosTestField;
    }

}
