package com.notification_service.listener;

import com.notification_service.DTO.OrderEvent;
import com.notification_service.DTO.RabbitConfig;
import com.notification_service.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService service;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consume(OrderEvent event) {

        log.info("📩 Event Received: {}", event);

        // ✅ FIXED MESSAGE BUILD
        String message = "Order ID: " + event.getOrderId()
                + " | Status: " + event.getStatus()
                + " | Amount: " + event.getAmount();

        // Save in DB
        service.save(event.getEmail(), message);

        // Simulate email
        System.out.println("=================================");
        System.out.println("📧 EMAIL SENT");
        System.out.println("To: " + event.getEmail());
        System.out.println("Message: " + message);
        System.out.println("=================================");
    }
}