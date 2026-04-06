package com.paymentservice;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/pay")
    public Payment pay(@RequestParam Long orderId,
                       @RequestParam Double amount) {

        return service.processPayment(orderId, amount);
    }
}