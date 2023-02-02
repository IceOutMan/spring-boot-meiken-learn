package com.meiken.springbootmeikendatasourcetkmybatis.config;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BaseMapper<T> extends Mapper<T>, ConditionMapper<T>, MySqlMapper<T> {
}
