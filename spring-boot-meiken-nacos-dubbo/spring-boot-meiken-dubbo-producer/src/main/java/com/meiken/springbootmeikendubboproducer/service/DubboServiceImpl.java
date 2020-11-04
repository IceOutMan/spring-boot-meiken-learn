package com.meiken.springbootmeikendubboproducer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.meiken.springbootmeikendubbocommon.service.ICommonDubboService;

/**
 * @Author glf
 * @Date 2020/9/2
 */
@Service(interfaceClass = ICommonDubboService.class,version = ICommonDubboService.VERSION)
public class DubboServiceImpl implements ICommonDubboService {

    @Override
    public String getName() throws Exception {
        return "Im Dubbo provider";
    }
}
