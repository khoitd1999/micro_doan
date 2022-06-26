package com.example.ordermodule.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public final static String exchange = "EXCHANGE.DOAN";

    public final static String queue_user_module = "QUEUE.USER.MODULE";
    public final static String queue_common_module = "QUEUE.COMMON.MODULE";
    public final static String queue_order_module = "QUEUE.ORDER.MODULE";
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue queueUserModule() {
        return new Queue(queue_user_module);
    }

    @Bean
    Queue queueCommonModule() {
        return new Queue(queue_common_module);
    }

    @Bean
    Queue queueOrderModule() {
        return new Queue(queue_order_module);
    }

    @Bean
    Binding bindingClient() {
        return BindingBuilder.bind(queueUserModule()).to(directExchange()).with(queue_user_module);
    }

    @Bean
    Binding bindingCommon() {
        return BindingBuilder.bind(queueCommonModule()).to(directExchange()).with(queue_common_module);
    }

    @Bean
    Binding bindingOrder() {
        return BindingBuilder.bind(queueOrderModule()).to(directExchange()).with(queue_order_module);
    }
}
