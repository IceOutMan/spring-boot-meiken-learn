package com.meiken.tracemessage;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 订阅消息时需要开启 traceMessage 模式
 */
public class TracePushConsumerExample {
    public static final String TOPIC = "TraceMessageTestTopic";

    public static void main(String[] args) throws Exception  {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("trace_message_consumer_group",true);
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe(TOPIC,"*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
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
