package com.meiken.service;

import com.meiken.ICommonZkDubboService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ZkDubboServiceImpl implements ICommonZkDubboService {
    @Override
    public String getServiceName(String name) {
        return "zk dubbo service :" + name;
    }
}
