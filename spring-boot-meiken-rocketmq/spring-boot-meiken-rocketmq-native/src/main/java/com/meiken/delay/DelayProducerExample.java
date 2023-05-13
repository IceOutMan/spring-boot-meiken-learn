package com.meiken.delay;


import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class DelayProducerExample {
    public static final String TOPIC = "DelayTimeTopic";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("delay_time_producer_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            try {
                Message msg = new Message(TOPIC, "TagA", "OrderID9527", "hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 延时等级：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
                msg.setDelayTimeLevel(3);
                // 同步发送 , 有发送是否成功的结果
                SendResult sendResult = producer.send(msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
