package com.meiken.springbootmeiknefilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringBootMeikneFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMeikneFilterApplication.class, args);
    }

}
