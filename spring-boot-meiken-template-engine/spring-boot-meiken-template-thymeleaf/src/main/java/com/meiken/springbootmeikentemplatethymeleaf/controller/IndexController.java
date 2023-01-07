package com.meiken.springbootmeikentemplatethymeleaf.controller;

import com.meiken.springbootmeikentemplatethymeleaf.bean.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @RequestMapping("/variableExpression")
    public ModelAndView hello(Map<String,Object> map,HttpSession httpSession) {
        httpSession.setAttribute("sessionKey", "sessionValue");
        Person person = new Person();
        person.setName("person");
        person.setAge(12);
        //通过 map 向前台页面传递数据
        map.put("value", "ZS LS WW");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("variable_expression");
        modelAndView.addObject(map);
        modelAndView.addObject(person);
        return modelAndView;
    }
}
