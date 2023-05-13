package com.meiken.transation;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionListenerImpl implements TransactionListener {
    private AtomicInteger transactionIndex = new AtomicInteger();
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * half 消息
     * COMMIT_MESSAGE -> 消息直接可以被消费
     * ROLLBACK_MESSAGE -> 消息不能被消费
     * UNKNOW -> 消息暂时不能被消费 ，需要 checkLocalTransaction 确认
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        // half 消息
        System.out.println(Thread.currentThread().getName() + "#" + "executeLocalTransaction " + message.getTags() );

        String tags = message.getTags();
        if(StringUtils.contains(tags, "TagA")){
            return LocalTransactionState.COMMIT_MESSAGE;
        }else if(StringUtils.contains(tags, "TagB")){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }else{
            return LocalTransactionState.UNKNOW;
        }
    }

    /**
     * 本地事务检查
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println(Thread.currentThread().getName() + "#" + "checkLocalTransaction " + messageExt.getTags() );
        String tags = messageExt.getTags();
        if(StringUtils.contains(tags, "TagC")){
            return LocalTransactionState.COMMIT_MESSAGE;
        }else if(StringUtils.contains(tags, "TagD")){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }else{
            return LocalTransactionState.UNKNOW;
        }
    }
}
