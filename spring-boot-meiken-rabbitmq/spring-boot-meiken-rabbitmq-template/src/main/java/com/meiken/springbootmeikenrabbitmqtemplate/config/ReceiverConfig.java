package com.meiken.springbootmeikenrabbitmqtemplate.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

@Configuration
public class ReceiverConfig {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME,ackMode = "MANUAL")
    public void mqConsume(Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, Message message) throws IOException {

        System.out.println(new String(message.getBody()));

        //手动确认-消费成功-消费被服务端丢弃
//        System.out.println("消费成功:" + tag);
//        channel.basicAck(tag,false);

        //消费失败-可以重新入队，不重新入队则被服务端丢弃
//        System.out.println("丢弃，可重新入队:" + tag);
//        channel.basicNack(tag, false, true);

        //消费拒绝，直接被服务端丢弃
        System.out.println("消费拒绝:" + tag);
        channel.basicReject(tag,true);

    }
}
