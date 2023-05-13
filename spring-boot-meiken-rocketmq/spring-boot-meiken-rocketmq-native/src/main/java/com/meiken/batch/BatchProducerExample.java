package com.meiken.batch;


import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. 批量消息大小不能超过4M限制
 * 2. 批量的 msg 必须是发送到同一个Topic
 */
public class BatchProducerExample {
    public static final String TOPIC = "BatchTopic";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("batch_producer_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message(TOPIC, "TagA", "OrderID9527", "hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            messageList.add(msg);
        }

        // 批量发送
        try {
            SendResult batchSendResult = producer.send(messageList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        producer.shutdown();
    }
}
