package com.meiken.format;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 这里只是用了 ConfigurationProperties 注解，没有使用Component实例化改类
 * 在 MeikenLogFormatAutoConfiguration 类中使用了 EnableConfigurationProperties将改配置注入
 * @Author glf
 * @Date 2022/4/17
 */
@ConfigurationProperties(prefix = "meiken.format")
public class MeikenFormatProperties {

    private String appOwner;

    private Map<String,Object> infos;

    public String getAppOwner() {
        return appOwner;
    }

    public void setAppOwner(String appOwner) {
        this.appOwner = appOwner;
    }

    public Map<String, Object> getInfos() {
        return infos;
    }

    public void setInfos(Map<String, Object> infos) {
        this.infos = infos;
    }
}
