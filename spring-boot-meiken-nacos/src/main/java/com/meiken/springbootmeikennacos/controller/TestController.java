package com.meiken.springbootmeikennacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

/**
 * @Author glf
 * @Date 2020/8/27
 */
@RequestMapping("/nacos")
@RestController
public class TestController {

    @NacosValue(value = "${nacosTestField:isNull}",autoRefreshed = true)
    private String nacosTestField;


    @Autowired
    private DataSource dataSource;

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return nacosTestField;
    }


    @GetMapping("/datasourceConfig")
    @ResponseBody
    public String dataSourceConfig(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        int rowCount = jdbcTemplate.queryForObject("select count(*) from Teacher", Integer.class);

        return rowCount+"";
    }
}
