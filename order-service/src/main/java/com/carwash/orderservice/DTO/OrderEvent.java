package com.carwash.orderservice.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {

    private Long orderId;
    private String email;
    private Double amount;
    private String status;
}