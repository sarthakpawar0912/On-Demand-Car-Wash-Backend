package com.carservice.DTO;

import lombok.Data;

@Data
public class CarRequest {


    private String brand;
    private String model;
    private String color;
    private String numberPlate;
}
