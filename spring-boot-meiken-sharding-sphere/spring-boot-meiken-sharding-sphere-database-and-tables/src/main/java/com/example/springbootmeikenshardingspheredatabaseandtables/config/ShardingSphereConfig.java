package com.example.springbootmeikenshardingspheredatabaseandtables.config;

import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class ShardingSphereConfig {

    @Bean
    public DataSource dataSource() throws SQLException {
        return new ShardingDataSource(null ,null ,null) ;
    }

}
