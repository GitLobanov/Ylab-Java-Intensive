package com.backend.service;

import com.backend.model.Car;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    CarRepository carRepository;
    OrderRepository orderRepository;

    public CarService () {
        carRepository = new CarRepository();
        orderRepository = new OrderRepository();
    }

    public boolean addCar(Car car) {
        return carRepository.save(car);
    }


    public boolean updateCar(Car car) {
        return carRepository.update(car);
    }

    public boolean deleteCar(Car car) {
        return carRepository.delete(car);
    }

    public Car findCarById(int id) {
        Car car = carRepository.findById(id);
        return car;
    }

    public List<Car> getAvailableCars() {
        List<Car> list = carRepository.findAllAvailableCars();
        return list;
    }

    public Optional<Car> getCarById(int id) {
        Optional<Car> optional = carRepository.findById(id)!=null
                ? Optional.of(carRepository.findById(id)) : Optional.empty();
        return optional;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }


}
