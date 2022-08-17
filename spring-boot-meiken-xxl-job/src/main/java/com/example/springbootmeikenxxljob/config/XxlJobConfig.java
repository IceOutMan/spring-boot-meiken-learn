package com.example.springbootmeikenxxljob.config;


import com.alibaba.fastjson.JSON;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class XxlJobConfig {
    @Value("${xxl.job.executor.appname}")
    private String appName = "";
    @Value("${xxl.job.admin.addresses}")
    private String addresses = "";

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(addresses);
        xxlJobSpringExecutor.setAppname(appName);
        xxlJobSpringExecutor.setIp(null);
        xxlJobSpringExecutor.setAccessToken("");
        xxlJobSpringExecutor.setLogPath("/tmp/data/applogs/xxl-job/jobhandler");
        xxlJobSpringExecutor.setLogRetentionDays(14);

        return xxlJobSpringExecutor;
    }
}
