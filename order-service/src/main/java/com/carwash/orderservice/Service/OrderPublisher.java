package com.carwash.orderservice.Service;

import com.carwash.orderservice.DTO.OrderEvent;
import com.carwash.orderservice.configs.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate; // 🔥 ADD THIS
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendOrderEvent(OrderEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}