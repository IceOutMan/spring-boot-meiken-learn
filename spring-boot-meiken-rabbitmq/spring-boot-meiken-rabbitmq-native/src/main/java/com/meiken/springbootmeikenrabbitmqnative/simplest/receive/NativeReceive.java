package com.meiken.springbootmeikenrabbitmqnative.simplest.receive;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author glf
 * @Date 2020/8/27
 */
public class NativeReceive {

    private final static String QUEUE_NAME = "MEIKEN_QUEUE";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");


        try( Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false,false,false,null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag,delivery)->{
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };

            CancelCallback cancelCallback = consumerTag -> {
                System.out.println("cancle callback");
            };

            channel.basicConsume(QUEUE_NAME,true,deliverCallback, cancelCallback);


            Thread.sleep(5000);

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
