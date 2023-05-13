package com.meiken.broadcast;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class BroadCastProducerExample {
    public static final String TOPIC = "BroadCastTestTopic";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("broad_cast_use_producer_group");

        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i = 0; i < 50; i++) {
            try {
                String body = "order_" + i;
                Message msg = new Message(TOPIC, "Order", "OrderID9527", body.getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 同步发送 , 有发送是否成功的结果
                SendResult sendResult = producer.send(msg);
                System.out.println(JSON.toJSONString(sendResult));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        producer.shutdown();
    }
}
