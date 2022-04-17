package com.meiken.format;

import com.meiken.format.impl.JsonFormatHandler;
import com.meiken.format.impl.StringFormatHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

/**
 * 路径配置在 springs.factory 中，会被spring boot 的AutoConfiguration自动加载
 * @Author glf
 * @Date 2022/4/17
 */
@Import(MeikenFormatTemplate.class)
@EnableConfigurationProperties(MeikenFormatProperties.class)
@Configuration
public class MeikenLogFormatAutoConfiguration {

    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
    @Bean
    @Primary
    public FormatHandler stringFormat(){
        return new StringFormatHandler();
    }

    // 可以保证在有fastjson包加载进来的时候才实例话
    @ConditionalOnClass(name = "com.alibaba.fastjson.JSON")
    @Bean
    public FormatHandler jsonFormat(){
        return new JsonFormatHandler();
    }

    @Bean
    public MeikenFormatTemplate meikenFormatTemplate(FormatHandler formatHandler,MeikenFormatProperties meikenFormatProperties){
        return new MeikenFormatTemplate(formatHandler, meikenFormatProperties);
    }

}
