package com.example.usermodule.config;

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
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue queueUserModule() {
        return new Queue(queue_user_module);
    }

    @Bean
    Binding bindingClient() {
        return BindingBuilder.bind(queueUserModule()).to(directExchange()).with(queue_user_module);
    }
}
