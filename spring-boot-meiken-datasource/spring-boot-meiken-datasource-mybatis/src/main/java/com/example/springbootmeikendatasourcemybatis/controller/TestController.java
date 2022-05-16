package com.example.springbootmeikendatasourcemybatis.controller;


import com.example.springbootmeikendatasourcemybatis.dao.UserTestMapper;
import com.example.springbootmeikendatasourcemybatis.entity.UserTest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
public class TestController {

    @Autowired
    private UserTestMapper userTestMapper;

    @RequestMapping("/pageQuery")
    public void pageQuery(){
        PageHelper.startPage(1,2);
        List<UserTest> userList = userTestMapper.selectAll();
        System.out.println(userList);
        PageInfo<UserTest> userTestPageInfo = new PageInfo<>(userList);
        PageHelper.clearPage();
        System.out.println(userTestPageInfo);

    }
}
