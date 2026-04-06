package com.notification_service.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

    private Long orderId;
    private String email;
    private Double amount;
    private String status;
}