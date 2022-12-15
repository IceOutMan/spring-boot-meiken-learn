package com.meiken.springbootmeikenloglogback;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class LogBack_SLF4j_TEST {

    @Test
    void contextLoads() {
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }

}
