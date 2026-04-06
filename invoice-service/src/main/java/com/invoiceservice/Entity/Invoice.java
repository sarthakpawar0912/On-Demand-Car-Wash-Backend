package com.invoiceservice.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long orderId;

    private Double amount;
    private Double cgst;
    private Double sgst;
    private Double totalAmount;

    private String invoiceNumber;
    private String filePath;

    private LocalDateTime createdAt;
}