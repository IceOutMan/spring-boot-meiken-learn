package com.meiken.springbootmeikenrabbitmqtemplate.controller;

import com.meiken.springbootmeikenrabbitmqtemplate.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author glf
 * @Date 2020/11/3
 */
@RestController
@RequestMapping("/rabbit_mq")
public class RabbitMQController {


    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/send")
    public void send(@RequestBody  String msg){
//        amqpTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, msg);
        amqpTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE,"DIRECT_KEY",msg);
    }

    @GetMapping("/receive")
    @ResponseBody
    public Object receive(){
        return amqpTemplate.receiveAndConvert(RabbitMQConfig.QUEUE_NAME);
    }


}
