package com.meiken.springbootmeikenstatertest.controller;

import com.meiken.format.MeikenFormatTemplate;
import com.meiken.springbootmeikenstatertest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author glf
 * @Date 2022/4/17
 */
@RequestMapping
@RestController
public class IndexController {

    @Autowired
    private MeikenFormatTemplate meikenFormatTemplate;

    @GetMapping("/index")
    @ResponseBody
    public String index(){
        User user = new User();
        user.setName("张三");
        user.setAddress("四川");

        return meikenFormatTemplate.doFormat(user);
    }
}
