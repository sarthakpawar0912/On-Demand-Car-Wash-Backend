package com.carwash.orderservice.Entity;

import com.carwash.orderservice.DTO.Car;
import com.carwash.orderservice.configs.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "CAR-SERVICE", configuration = FeignConfig.class)
public interface CarClient {

    @GetMapping("/car/user/{userId}")
    List<Car> getCars(@PathVariable Long userId);
}