package com.meiken;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class Log4j2_TEST {

    private static Logger logger = LogManager.getLogger(Log4j2_TEST.class);
    private static Logger meikenLogger = LogManager.getLogger("meiken.log");

    @Test
    void contextLoads() throws InterruptedException {
        while (true) {

            logger.debug("debug");
            logger.info("info");
            logger.warn("warn");
            logger.error("error");
            Thread.sleep(1000);

//            meikenLogger.debug("debug");
//            meikenLogger.warn("warn");
//            meikenLogger.error("error");
        }
    }


}
