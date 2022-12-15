package com.meiken.springbootmeikenloglogback;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author glf
 * @Date 2021/9/7
 */
@RequestMapping(value = "/index")
@RestController
@Slf4j
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/slf4jLoggerOk")
    public void slf4jLoggerOk() {
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }


    @GetMapping("/annotationLoggerOk")
    public void annotationLoggerOk() {
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }

}