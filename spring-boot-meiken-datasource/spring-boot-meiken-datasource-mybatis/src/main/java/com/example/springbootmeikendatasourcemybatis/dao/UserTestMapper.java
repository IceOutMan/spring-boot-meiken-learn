package com.example.springbootmeikendatasourcemybatis.dao;

import com.example.springbootmeikendatasourcemybatis.entity.UserTest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserTestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTest record);

    UserTest selectByPrimaryKey(Integer id);

    List<UserTest> selectAll();

    int updateByPrimaryKey(UserTest record);
}