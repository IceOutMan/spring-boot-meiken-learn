package com.meiken.springbootmeikenshardingspheretable.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author glf
 * @Date 2021/4/22
 */
@Mapper
@Component
public interface OrderDao {

    @Insert("insert into t_order(price, user_id,status) values (#{price} ,#{userId}, #{status} )")
    int insertOrder(@Param("price") BigDecimal price, @Param("userId") Long userId, @Param("status") String status);
}
