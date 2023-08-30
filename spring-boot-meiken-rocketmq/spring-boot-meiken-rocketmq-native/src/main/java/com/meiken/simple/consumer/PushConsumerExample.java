package com.meiken.simple.consumer;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 服务器给 Consumer 推送消息
 */
public class PushConsumerExample {
    public static final String TOPIC = "TestTopic";

    public static void main(String[] args) throws Exception  {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PUSH_CONSUMER_GROUP");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe(TOPIC,"*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setConsumeTimestamp("20181109221800");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("Receive New message : " + Thread.currentThread().getName() + "  #  " + JSON.toJSONString(msgs));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("Push Consumer Started");
    }
}
