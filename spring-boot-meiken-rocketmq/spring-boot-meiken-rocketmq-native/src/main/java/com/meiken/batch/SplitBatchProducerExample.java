package com.meiken.batch;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

public class SplitBatchProducerExample {
    public static final String TOPIC = "SplitBatchTopic";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("split_batch_producer_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message(TOPIC, "TagA", "OrderID9527", "hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            messageList.add(msg);
        }

        // 批量发送
        try {
            // 自己拆成小批量 如 5个一批
            List<List<Message>> partitionMessageList = Lists.partition(messageList, 5);
            partitionMessageList.forEach( msgList -> {
                try {
                    System.out.println(JSON.toJSONString(msgList));
                    SendResult batchSendResult = producer.send(messageList);
                } catch (MQClientException e) {
                    throw new RuntimeException(e);
                } catch (RemotingException e) {
                    throw new RuntimeException(e);
                } catch (MQBrokerException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        producer.shutdown();
    }
}
