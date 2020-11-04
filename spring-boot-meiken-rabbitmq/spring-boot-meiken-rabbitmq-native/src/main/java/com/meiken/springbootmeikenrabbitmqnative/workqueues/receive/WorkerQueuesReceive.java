package com.meiken.springbootmeikenrabbitmqnative.workqueues.receive;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author glf
 * @Date 2020/9/26
 */
public class WorkerQueuesReceive {

    private final static String QUEUE_NAME = "MEIKEN_QUEUE";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");


        try( Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, true,false,false,null);
            //消费端的只能有一个未被确认的消息，一个消费完确认后才能收到下一个，不会一次缓存多个
//            channel.basicQos(1);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag,delivery)->{
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");

                try {
                    doTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(" [x] Done");

                    //手动确认-消费成功-消费被服务端丢弃
//                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                    //消费失败-可以重新入队，不重新入队则被服务端丢弃
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(),false,false);
                    //消费拒绝，直接被服务端丢弃
//                    channel.basicReject(delivery.getEnvelope().getDeliveryTag(),false);

//                    channel.basicReject();
                }
            };

            CancelCallback cancelCallback = consumerTag -> {
                System.out.println("cancle callback");
            };

            //关闭自动确认，进行手动确认
            boolean autoAckFlag = false;
            channel.basicConsume(QUEUE_NAME,autoAckFlag,deliverCallback, cancelCallback);
            Thread.sleep(1000000);

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void doTask() throws InterruptedException {
        Thread.sleep(2000);
    }
}
