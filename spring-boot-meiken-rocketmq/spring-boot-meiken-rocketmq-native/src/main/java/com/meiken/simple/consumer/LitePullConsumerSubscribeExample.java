package com.meiken.simple.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;


import java.util.ArrayList;
import java.util.List;

public class LitePullConsumerSubscribeExample {

    public static final String TOPIC = "TestTopic";
    public static final boolean RUNNING = true;
    public static void main(String[] args) throws MQClientException {
        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer("lite_pull_consumer_subscribe_group");
        litePullConsumer.setNamesrvAddr("localhost:9876");
        litePullConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        litePullConsumer.subscribe(TOPIC, "*");
        // 是否自动提交 commit
        litePullConsumer.setAutoCommit(false);
        litePullConsumer.start();

        try {
            while (RUNNING){
                List<MessageExt> messageExtList = litePullConsumer.poll();
                System.out.println("Get message from MessageQueue : " + JSON.toJSONString(messageExtList));
            }
        }catch (Exception e){
            litePullConsumer.shutdown();
        }finally {
            litePullConsumer.shutdown();
        }
    }

}
