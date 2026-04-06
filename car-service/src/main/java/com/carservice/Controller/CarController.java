package com.carservice.Controller;

import com.carservice.DTO.CarRequest;
import com.carservice.Entity.Car;
import com.carservice.Service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/add")
    public Car addCar(@RequestBody CarRequest req,
                      @RequestHeader("X-User-Email") String email) {
        // TODO: look up userId from email via Feign or pass userId from order-service
        Long userid = 1L;
        return carService.addCar(req, userid);
    }

    @GetMapping("/user/{userId}")
    public List<Car> getCars(@PathVariable Long userId) {
        return carService.getCarsByUser(userId);
    }

    @PutMapping("/update/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody CarRequest req) {
        return carService.updateCar(id, req);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deletecar(id);
        return "Car deleted Successfully";
    }
}
