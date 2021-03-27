package com.meiken.springbootmeikendubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.meiken.springbootmeikendubbocommon.service.ICommonDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author glf
 * @Date 2020/9/4
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @DubboReference(version = ICommonDubboService.VERSION)
    private ICommonDubboService iCommonDubboService;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String consumerTest() throws Exception {
        return iCommonDubboService.getName();
    }
}
