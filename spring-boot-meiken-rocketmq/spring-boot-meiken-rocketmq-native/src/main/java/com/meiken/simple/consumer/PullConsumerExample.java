package com.meiken.simple.consumer;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 已废弃 不建议使用
 * Consumer 主动从服务器 拉消息
 */
public class PullConsumerExample {
    public static final String TOPIC = "TestTopic";
    public static final HashMap<MessageQueue, Long> OFFSET_TABLE = new HashMap<MessageQueue,Long>();

    public static void main(String[] args) throws Exception {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("PULL_CONSUMER_GROUP");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.start();

        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues(TOPIC);
        messageQueues.forEach(messageQueue -> {
            System.out.println("Consume from messageQueue : " + JSON.toJSONString(messageQueue));
            SINGLE_MQ:
            while (true) {
                try {
                    PullResult pullResult = consumer.pullBlockIfNotFound(messageQueue, null, getMessageQueueOffset(messageQueue), 32);
                    System.out.println("Get msg from MessageQueue : " + JSON.toJSONString(pullResult));
                    putMessageQueueOffset(messageQueue, pullResult.getNextBeginOffset());

                    // 根据状态判读如何处理
                    switch (pullResult.getPullStatus()){
                        case FOUND:
                            break ;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            // 换个队列获取
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break ;
                        default:
                            break ;
                    }
                } catch (Exception e) {

                }
            }


        });

        System.out.println("Push Consumer Started");
    }

    public static long getMessageQueueOffset(MessageQueue messageQueue){
        Long offset = OFFSET_TABLE.get(messageQueue);
        return offset != null ? offset : 0;
    }

    public static void putMessageQueueOffset(MessageQueue messageQueue, Long offset){
        OFFSET_TABLE.put(messageQueue, offset);
    }
}
