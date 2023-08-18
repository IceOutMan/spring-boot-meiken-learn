package com.meiken.order;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 只保证局部有序
 */
public class OrderConsumerExample {
    public static void main(String[] args) {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_consumer_group");
            consumer.setNamesrvAddr("localhost:9876");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

            consumer.subscribe("OrderTopicTest", "*");
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                    // 一个队列一个队列的推过来
                    // 自动 commit
                    consumeOrderlyContext.setAutoCommit(true);
                    for(MessageExt messageExt : list){
                        System.out.println("收到消息内容：" +  JSON.toJSONString(new String(messageExt.getBody())));
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });

            consumer.start();
            System.out.println("Order Consume Start");

        }catch (Exception e){

        }
    }
}
