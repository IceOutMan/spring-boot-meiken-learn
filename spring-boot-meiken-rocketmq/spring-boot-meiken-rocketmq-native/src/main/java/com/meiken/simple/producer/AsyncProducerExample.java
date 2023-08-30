package com.meiken.simple.producer;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class AsyncProducerExample {
    public static final String TOPIC = "TestTopic";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("localhost:9876");

        // 异步发送失败，重试的次数 次数设置为0不重试
        producer.setRetryTimesWhenSendAsyncFailed(0);
        producer.start();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            try {
                Message msg = new Message(TOPIC, "TagA", "OrderID9527", "hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 异步发送
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println(JSON.toJSONString(sendResult));
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        System.out.println("Send error: " + msg);
                        countDownLatch.countDown();
                    }
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        countDownLatch.await();
    }
}
