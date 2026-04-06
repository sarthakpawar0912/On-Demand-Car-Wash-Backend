package com.carwash.orderservice.DTO;

import lombok.Data;

@Data
public class Car {

    private Long id;
    private Long userId;

    private String brand;
    private String model;
    private String color;
    private String numberPlate;
}