package com.meiken.springbootmeiknefilter.config;

import com.meiken.springbootmeiknefilter.filters.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;


/**
 * @Author glf
 * @Date 2021/9/27
 */
@Configuration
public class MeikenFilterConfig {

    @Bean
    public Filter logFilter(){
        return new LogFilter();
    }

    @Bean
    public FilterRegistrationBean setLogFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(logFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(1);   //order的数值越小，在所有的filter中优先级越高
        return filterRegistrationBean;
    }

}
