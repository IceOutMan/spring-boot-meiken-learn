package com.meiken.filter.sql;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SqlFilterProducerExample {
    public static final String TOPIC = "SqlFilterTestTopic";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("sql_filter_use_producer_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        String[] tagArray = new String[]{"TagA", "TagB", "TagC"};

        for (int i = 0; i < 50; i++) {
            try {
                String tag = tagArray[i % tagArray.length];
                String body = tag + "_" + i;
                Message msg = new Message(TOPIC, tag, "OrderID9527", body.getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 设置自己的属性
                msg.putUserProperty("a",String.valueOf(i));
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
