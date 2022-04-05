package com.meiken.properties.controller;

import com.meiken.properties.config.ConfigurationPropertiesBeanConfig;
import com.meiken.properties.config.PropertySourceBeanConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {
    @Autowired
    private ConfigurationPropertiesBeanConfig configurationPropertiesBeanConfig;
    @Autowired
    private PropertySourceBeanConfig propertySourceBeanConfig;

    @GetMapping(value = "/properties")
    public Object index(){
        Map<String,Object> result = new HashMap<>();

        result.put("propertySource", propertySourceBeanConfig);
        result.put("configurationProperties", configurationPropertiesBeanConfig);

        return result;

    }
}
