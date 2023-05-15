package com.meiken.tracemessage;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * broker 配置：traceTopicEnable = true
 * 默认轨迹存在 RMQ_SYS_TRACE_TOPIC
 * 发送消息需要开启轨迹
 */
public class TraceProducerExample {
    public static final String TOPIC = "TraceMessageTestTopic";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("trace_message_producer_group", true);
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i = 0; i <128; i++) {
            try {
                Message msg = new Message(TOPIC, "TagA", "OrderID95272", "hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
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
