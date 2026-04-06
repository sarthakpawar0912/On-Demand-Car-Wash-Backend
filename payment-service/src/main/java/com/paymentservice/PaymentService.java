package com.paymentservice;


import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class PaymentService {

    public Payment processPayment(Long orderId, Double amount) {

        System.out.println("💰 Processing payment...");
        System.out.println("Order: " + orderId);

        // 💡 Always success (dummy)
        return new Payment(orderId, amount, "SUCCESS");
    }
}