package com.meiken.simple.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LitePullConsumerAssignExample {
    public static final String TOPIC = "TestTopic";

    public static final boolean RUNNING = true;
    public static void main(String[] args) throws MQClientException {

        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer("lite_pull_consumer_assign_group");
        litePullConsumer.setNamesrvAddr("localhost:9876");
        // 是否自动提交 commit
        litePullConsumer.setAutoCommit(false);
        litePullConsumer.start();

        ArrayList<MessageQueue> messageQueueList = new ArrayList<>(litePullConsumer.fetchMessageQueues(TOPIC));
        ArrayList<MessageQueue> assignList  = new ArrayList<>();

        for(int i=0; i< messageQueueList.size() / 2; i++){
            assignList.add(messageQueueList.get(i));
        }

        litePullConsumer.assign(assignList);
        litePullConsumer.seek(assignList.get(0), 10);

        try {
            while (RUNNING){
                List<MessageExt> messageExtList = litePullConsumer.poll();
                System.out.println("Get message from MessageQueue : " + JSON.toJSONString(messageExtList));
                litePullConsumer.commitSync(); // 提交 commit
            }

        }catch (Exception e){
            litePullConsumer.shutdown();
        }
    }

}
