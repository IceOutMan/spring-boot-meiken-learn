package com.meiken.simple.producer;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 发送消息
 */
public class ProducerExample {
    public static final String TOPIC = "TestTopic";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("localhost:9876");
        // 异步发送失败，重试次数，此处不使用异步可以不使用该步骤
        producer.setRetryTimesWhenSendAsyncFailed(0);
        producer.start();

        for (int i = 0; i < 10; i++) {
            try {
                Message msg = new Message(
                        TOPIC,
                        "TagA",
                        "OrderID9527",
                        "hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 单向发送，不关心是否发送成功, 快
                producer.sendOneway(msg);

                // 同步发送 , 有发送是否成功的结果
//                SendResult sendResult = producer.send(msg);
//                System.out.println(JSON.toJSONString(sendResult));

                // 异步发送 -> AsyncProducerExample


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
