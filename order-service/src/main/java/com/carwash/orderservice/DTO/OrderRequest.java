package com.carwash.orderservice.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRequest {

    private Integer carId;
    private String serviceType;
    private LocalDateTime scheduledAt;

}
