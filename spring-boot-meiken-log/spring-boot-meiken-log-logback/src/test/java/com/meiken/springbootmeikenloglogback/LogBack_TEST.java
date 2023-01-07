package com.meiken.springbootmeikenloglogback;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogBack_TEST {


    @Test
    void contextLoads() {
        Logger logger = LoggerFactory.getLogger(LogBack_TEST.class);
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

}
