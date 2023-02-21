package com.meiken.springbootmeikeninterceptor.config;

import com.meiken.springbootmeikeninterceptor.interceptors.AuthIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author glf
 * @Date 2021/9/27
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthIntercepter authIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authIntercepter);
    }
}
