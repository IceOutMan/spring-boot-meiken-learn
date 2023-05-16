package com.meiken.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import sun.swing.StringUIClientPropertyKey;

import javax.xml.bind.SchemaOutputResolver;
import java.util.concurrent.ConcurrentHashMap;


@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
public class MyTransactionImpl implements RocketMQLocalTransactionListener {
    private ConcurrentHashMap<Object, Message> localTrans = new ConcurrentHashMap<>();

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        String transId = message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TRANSACTION_ID).toString();
        String topic = message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TOPIC).toString();
        String tags = message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TAGS).toString();

        localTrans.put(transId, message);
        // 这个 msg 的实现类是 GenericMessage， 里面实现了 toString 方法
        // 在 Header 中自定义的 RocketMQHeader.TAGS 属性，到这里就没有了，但是 RocketMQHeaders.TRANSACTION_ID 这个属性就还存在
        // message 的 Header 里面会默认保存 RocketMQHeaders里的属性，但是都会加上一个 RocketMQHeaders.PREFIX 前缀
        System.out.println("executeLocalTransaction msg = " + message);
        // 转成 RocketMQ 的 Message 对象
        if (StringUtils.contains(tags, "TagA")) {
            return RocketMQLocalTransactionState.COMMIT;
        } else if (StringUtils.contains(tags, "TagB")) {
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    // 上述执行是 UNKNOWN ， 后续一段时间间隔后会执行 checkLocalTransaction
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String transId = message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TRANSACTION_ID).toString();
        Message originMessage = localTrans.get(transId);
        // 这里能够获取到自定义的 transaction_id 属性
        System.out.println("checkLocalTransaction msg = " + originMessage);
        if(originMessage == null){
            System.out.println("origin msg is null = " + transId);
           return RocketMQLocalTransactionState.ROLLBACK ;
        }
        // 获取标签的时候， 自定义的 RocketMQHeaders.TAGS 拿不到， 但是框架会封装成一个带 RocketMQHeaders.PREFIX 的属性
        // String tags = msg.getHeaders().get(RocketMQHeaders.TAGS).toString();
        String tags = message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TAGS).toString();
        if (StringUtils.contains(tags, "TagC")) {
            return RocketMQLocalTransactionState.COMMIT;
        } else if (StringUtils.contains(tags, "TagD") || StringUtils.contains(tags, "TagE")) {
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
