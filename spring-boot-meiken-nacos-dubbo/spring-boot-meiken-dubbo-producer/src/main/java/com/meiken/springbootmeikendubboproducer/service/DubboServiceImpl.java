package com.meiken.springbootmeikendubboproducer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.meiken.springbootmeikendubbocommon.service.ICommonDubboService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author glf
 * @Date 2020/9/2
 */
@DubboService(interfaceClass = ICommonDubboService.class, version = ICommonDubboService.VERSION)
public class DubboServiceImpl implements ICommonDubboService {

    @Override
    public String getName() throws Exception {
        System.out.println("getName");
        return "Im Dubbo provider";
    }
}
