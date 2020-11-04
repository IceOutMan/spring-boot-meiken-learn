package com.meiken.springbootmeikenrabbitmqnative.topic.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Author glf
 * @Date 2020/11/3
 */
public class TopicSend {

    public final static String EXCHANGE_NAME = "TOPIC_EXCHANGE";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");


        try(Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME,"topic");

            for(int i=0;i<5;i++){
                String message =  i + " Message";
                byte[] messageBody = message.getBytes(StandardCharsets.UTF_8);
                channel.basicPublish(EXCHANGE_NAME,"kern.critical",null, messageBody);
                System.out.println("[X]:Send " + EXCHANGE_NAME + " - kern.critical" + " - "+message);
            }

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
