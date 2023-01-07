package com.meiken;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author glf
 * @Date 2021/9/6
 */
@RequestMapping(value = "/index")
@RestController
public class IndexController {

    @GetMapping("/ok")
    public void logOk() {
//        log.debug("debug");
//        log.info("info");
//        log.warn("warn");
//        log.error("error");
    }

}
