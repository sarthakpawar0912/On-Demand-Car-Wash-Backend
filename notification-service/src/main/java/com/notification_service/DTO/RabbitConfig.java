package com.notification_service.DTO;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.*;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
public class RabbitConfig {

    public static final String QUEUE = "order_queue";
    public static final String EXCHANGE = "order.exchange";
    public static final String ROUTING_KEY = "order.routing";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    // ✅ USE THIS (WORKING)
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}