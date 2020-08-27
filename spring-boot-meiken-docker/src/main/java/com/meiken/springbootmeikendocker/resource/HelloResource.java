package com.meiken.springbootmeikendocker.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author glf
 * @Date 2020/6/13
 */
@RestController
@RequestMapping("/rest/docker/hello")
public class HelloResource {

    @GetMapping
    public String test() {
        return "HELLO YOU";
    }
}
