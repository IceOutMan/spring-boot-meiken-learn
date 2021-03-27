package com.meiken.springbootmeikenrabbitmqnative.direct.receive;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author glf
 * @Date 2020/11/3
 */
public class DirectReceive {

    public final static String EXCHANGE_NAME = "DIRECT_EXCHANGE";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        try (Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            //获取一个随机的queue，连接断了自动删除
            String tempQueueName = channel.queueDeclare().getQueue();
            channel.queueBind(tempQueueName, EXCHANGE_NAME, "meiken_direct");


            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };

            channel.basicConsume(tempQueueName, true, deliverCallback, consumerTag -> {
            });

            Thread.sleep(200000);


        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
