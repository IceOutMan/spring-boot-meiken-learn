package com.meiken.filter.sql;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * broker 需要开启支持sql过滤的配置
 *
 * 是否支持根据属性过滤 如果使用基于标准的sql92模式过滤消息则改参数必须设置为true
 * enablePropertyFilter=true
 */
public class SqlFilterPushConsumerExample {
    public static final String TOPIC = "SqlFilterTestTopic";

    public static void main(String[] args) throws Exception  {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sql_filter_consumer_group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe(TOPIC, MessageSelector.bySql("(TAGS is not null and TAGS in ('TagA', 'TagB')) and (a is not null and a between 1 and 20)"));

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

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
