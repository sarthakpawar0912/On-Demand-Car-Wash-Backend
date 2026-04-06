package com.carservice.Service;

import com.carservice.DTO.CarRequest;
import com.carservice.Entity.Car;
import com.carservice.Repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private  final CarRepository carRepository;

    public Car addCar(CarRequest carRequest,Long userId){
        Car car=new Car();
        car.setUserId(userId);
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setColor(carRequest.getColor());
        car.setNumberPlate(carRequest.getNumberPlate());

        return carRepository.save(car);
    }

    public List<Car> getCarsByUser(Long userId){
        return carRepository.findByUserId(userId);
    }

    public Car updateCar(Long id,CarRequest req){
        Car car=carRepository.findById(id).orElseThrow(()->new RuntimeException("car not found"));

        car.setBrand(req.getBrand());
        car.setModel(req.getModel());
        car.setColor(req.getColor());
        car.setNumberPlate(req.getNumberPlate());

        return carRepository.save(car);
    }

    public void deletecar(Long id){
        Car car=carRepository.findById(id).orElseThrow(()-> new RuntimeException("Car not found"));
        carRepository.delete(car);
    }
}
