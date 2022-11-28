package com.meiken.springbootmeikentemplatethymeleaf.controller;

import com.meiken.springbootmeikentemplatethymeleaf.config.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/expression")
public class PropertiesController {

    @RequestMapping("/hello")
    public ModelAndView hello(Map<String,Object> map, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        // controller 方法中的map，set的key和value 会作为 ModelAndView 的一个单独的对象
        map.put("name","this name");
        map.put("date", new Date());
        map.put("map",map);
        map.put("list", Arrays.asList(1,2,3,4,5));
        map.put("set", new HashSet<Integer>(Arrays.asList(1,2,3,4)));

        Person person = new Person();
        person.setAge(10);
        person.setSex(1);
        person.setName("[this is person]");

        session.setAttribute("map",map);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("properties");
        modelAndView.addObject(map);
        modelAndView.addObject(person);
        return modelAndView;
    }
}
