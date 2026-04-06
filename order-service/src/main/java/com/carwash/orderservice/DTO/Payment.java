package com.carwash.orderservice.DTO;

import lombok.Data;

@Data
public class Payment {
    private Long id;
    private Long orderId;
    private Double amount;
    private String status;
}