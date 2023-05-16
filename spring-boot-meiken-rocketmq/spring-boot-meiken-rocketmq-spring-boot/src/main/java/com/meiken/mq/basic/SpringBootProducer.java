package com.meiken.mq.basic;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.websocket.SendResult;

@Component
public class SpringBootProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String topic, String msg){
        rocketMQTemplate.convertAndSend(topic, msg);
    }

    public void sendTransactionMessage(String topic, String msg) throws InterruptedException {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for(int i=0; i<10; i++){
            String tag = tags[i % tags.length];
            // 尝试在 Header 中加入一些自定义属性
            Message<String> message = MessageBuilder.withPayload(msg + "_" + tag)
                    // header 中有个单独属性 RocketMQHeaders.TRANSACTION_ID
                    .setHeader(RocketMQHeaders.TRANSACTION_ID, "TransId_" + i)
                    // 发到事务监听器里面后，这个自己设置的TAGS 属性会丢失, 会被设置为 RocketMQHeader.PREFIX + RocketMQHeader.TAGS，但上面的属性不会消失
                    .setHeader(RocketMQHeaders.TAGS, tag)
                    // MyProp 属性
                    .setHeader("MyProp", "MyProp_" + i)
                    .build();
            // destination 是 TOPIC:TAGS 的组合
            String destination = topic + ":" + tag;
            // 这里发送事务消息时，还是会转换成 RocketMQ 的 Message 对象，再调用 RocketMQ的 API完成事务消息机制
            TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, "this is arg");
            System.out.println(JSON.toJSONString(sendResult));

            Thread.sleep(10);
        }
    }

}
