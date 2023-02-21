package com.meiken.springbootmeiknefilter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author glf
 * @Date 2021/9/27
 */
@RequestMapping("/index")
@RestController
public class IndexController {

    @GetMapping("/ok")
    @ResponseBody
    public String ok(){
       return "OK";
    }

}
