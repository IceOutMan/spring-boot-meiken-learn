package com.meiken.controller;

import com.meiken.ICommonZkDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo")
public class DubboController {

    @DubboReference(interfaceClass = ICommonZkDubboService.class)
    ICommonZkDubboService zkDubboService;


    @GetMapping("/index")
    @ResponseBody
    public String index(){
        return zkDubboService.getServiceName("dubbo");
    }


}
