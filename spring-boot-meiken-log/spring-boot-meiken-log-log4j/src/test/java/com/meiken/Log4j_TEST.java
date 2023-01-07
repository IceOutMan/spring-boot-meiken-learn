package com.meiken;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Log4j_TEST {

    private static Logger logger = Logger.getLogger(Log4j_TEST.class);

    @Test
    void contextLoads() {
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

}
