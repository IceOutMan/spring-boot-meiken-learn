package com.meiken.springbootmeikendatasourcetkmybatis.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meiken.springbootmeikendatasourcetkmybatis.dao.UserTestMapper;
import com.meiken.springbootmeikendatasourcetkmybatis.entity.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {

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