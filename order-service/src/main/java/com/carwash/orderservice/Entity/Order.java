package com.carwash.orderservice.Entity;

import com.carwash.orderservice.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long carId;

    private String serviceType;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long washerId;

    private LocalDateTime scheduledAt;
    private LocalDateTime createdAt;

}
