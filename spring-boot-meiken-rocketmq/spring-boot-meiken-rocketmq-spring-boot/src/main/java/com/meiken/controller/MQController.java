package com.meiken.controller;

import com.meiken.mq.basic.SpringBootProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mq")
@RestController
public class MQController {

    private final String TOPIC = "SpringBootTopic";

    @Autowired
    private SpringBootProducer producer;

    @GetMapping("/sendMsg")
    public void sendMsg(String message){
        producer.sendMessage(TOPIC, message);
    }

    @GetMapping("/sendTransactionMsg")
    public void sendTransactionMsg(String message){
        try {
            producer.sendTransactionMessage(TOPIC, message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
