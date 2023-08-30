package com.meiken.order;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * 只保证局部有序
 */
public class OrderProducerExample {
    private static String TOPIC_ORDER = "OrderTopicTest";

    public static void main(String[] args) {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("ORDER_PRODUCER_GROUP");
            producer.setNamesrvAddr("localhost:9876");
            producer.start();

            for(int orderId=0; orderId<10; orderId++){
                // 同一订单ID的消息要保证有序
                for(int j = 0; j < 5; j++){
                    String body = "order id " + orderId + " step " + j ;
                    Message msg = new Message(TOPIC_ORDER, "order_" + orderId, "KEY" + orderId, body.getBytes(RemotingHelper.DEFAULT_CHARSET));

                    SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                            // 同一个 orderId 的消息发送到同一个队列中
                            // arg 是send 函数中的 orderId 参数
                            Integer id = (Integer) arg;
                            int index = id % list.size();
                            return list.get(index);
                        }
                    }, orderId);

                    System.out.println(JSON.toJSONString(sendResult));
                }
            }
            producer.shutdown();

        }catch (Exception e){
        }
    }
}

