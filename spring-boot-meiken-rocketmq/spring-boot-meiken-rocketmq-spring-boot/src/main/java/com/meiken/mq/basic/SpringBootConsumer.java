package com.meiken.mq.basic;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "spring_boot_consume_group", topic = "SpringBootTopic", consumeMode = ConsumeMode.CONCURRENTLY, messageModel = MessageModel.CLUSTERING)
public class SpringBootConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("Received message : " + message);
    }
}
