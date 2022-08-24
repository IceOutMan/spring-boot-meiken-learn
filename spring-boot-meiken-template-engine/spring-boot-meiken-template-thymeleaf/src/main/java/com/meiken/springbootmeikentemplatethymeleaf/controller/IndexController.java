package com.meiken.springbootmeikentemplatethymeleaf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class IndexController {

    @RequestMapping("/hello")
    public ModelAndView hello(Map<String, Object> map) {
        //通过 map 向前台页面传递数据
        map.put("name", "ZS LS WW");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        modelAndView.addObject(map);
        return modelAndView;
    }
}
