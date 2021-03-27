package com.meiken.mongolearn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author glf
 * @Date 2021/2/22
 */
@RestController
@RequestMapping("/mongo")
public class MongoController {

    @Autowired
    public MongoTemplate mongoTemplate;

    @RequestMapping(method = RequestMethod.GET,value = "/test")
    public void test(){
        List<User> userList = mongoTemplate.query(User.class).all();
        System.out.println(userList);
    }
}
