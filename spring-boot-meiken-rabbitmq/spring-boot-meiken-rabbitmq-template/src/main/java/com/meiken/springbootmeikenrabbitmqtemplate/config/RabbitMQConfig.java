package com.meiken.springbootmeikenrabbitmqtemplate.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author glf
 * @Date 2020/11/3
 */
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "AMQP_TEMPLATE_MEIKEN_QUEUE_NAME";
    public static final String DIRECT_EXCHANGE = "MEIKEN_DIRECT_EXCHANGE";

    @Bean
    public Queue queue_builder() {
        return QueueBuilder.durable(QUEUE_NAME)
                .build();
    }

    @Bean
    public Exchange exchange_builder() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Binding bind_builder() {
        return BindingBuilder.bind(queue_builder()).to(exchange_builder()).with("DIRECT_KEY").noargs();
    }


//    @Bean
//    public Declarables queue_declarable() {
//        return new Declarables(new Queue(QUEUE_NAME,true));
//    }
//    @Bean
//    public Declarables exchange_declarable() {
//        return new Declarables(new DirectExchange(DIRECT_EXCHANGE,true,false));
//    }
//
//    @Bean
//    public Declarables bind_declarable() {
//        return new Declarables(
//                new Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, DIRECT_EXCHANGE, "DIRECT_KEY", null));
//    }


//    @Bean
//    public Queue queue_new() {
//        return new Queue(QUEUE_NAME,true);
//    }
//
//    @Bean
//    public Exchange exchange_new() {
//        return new DirectExchange(DIRECT_EXCHANGE,true,false);
//    }
//
//    @Bean
//    public Binding bind_new(){
//        return new  Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, DIRECT_EXCHANGE, "DIRECT_KEY", null);
//    }


//    @RabbitListener(queues = QUEUE_NAME)
//    public void meikenQueueListener(String msg){
//        System.out.println(msg);
//    }

}
