package com.carwash.orderservice.Entity;

import com.carwash.orderservice.DTO.Payment;
import com.carwash.orderservice.configs.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PAYMENT-SERVICE", configuration = FeignConfig.class)
public interface PaymentClient {

    @PostMapping("/payment/pay")
    Payment pay(@RequestParam Long orderId,
                @RequestParam Double amount);
}