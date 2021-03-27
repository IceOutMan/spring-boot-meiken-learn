package com.meiken.springbootmeikenrabbitmqnative.workqueues.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import sun.management.GcInfoBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Author glf
 * @Date 2020/9/26
 */
public class WorkerQueuesSend {


    public final static String QUEUE_NAME = "MEIKEN_QUEUE";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");


        try (Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            for (int i = 0; i < 5; i++) {
                String message = i + " Message";
                byte[] messageBody = message.getBytes(StandardCharsets.UTF_8);
                channel.basicPublish("", QUEUE_NAME, null, messageBody);
                System.out.println("[X]:Send " + message);
            }

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
