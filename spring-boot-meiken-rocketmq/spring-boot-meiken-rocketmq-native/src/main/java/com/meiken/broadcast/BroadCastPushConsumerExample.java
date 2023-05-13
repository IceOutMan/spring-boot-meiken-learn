package com.meiken.broadcast;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 默认消息是 集群模式
 * 如果是广播需要设置
 */
public class BroadCastPushConsumerExample {
    public static final String TOPIC = "BroadCastTestTopic";

    public static void main(String[] args) throws Exception  {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("broad_cast_consumer_group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe(TOPIC,"*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为广播模式， 同一个消费组的消费者都要消费一遍
        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for(MessageExt msg : msgs){
                    System.out.println("Consumer message: " + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("Push Consumer Started");
    }
}
