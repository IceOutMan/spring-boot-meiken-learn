package com.meiken.springbootmeikenrabbitmqnative.simplest.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * @Author glf
 * @Date 2020/8/27
 */
public class NativeSend {

    private final static String QUEUE_NAME = "MEIKEN_QUEUE";

    public static void main(String[] args) {
//
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");


        try(Connection connection = connectionFactory.newConnection()){


            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false,false,false,null);


            String message = "Hello im here";
            byte[] messageBody = message.getBytes(StandardCharsets.UTF_8);

            channel.basicPublish("",QUEUE_NAME,null,messageBody);

            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}