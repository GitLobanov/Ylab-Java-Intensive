package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.OrderRepository;

import java.util.List;

public class CarService {

    CarRepository carRepository;
    OrderRepository orderRepository;
    ActionLogService actionLogService;

    public CarService () {
        carRepository = new CarRepository();
        orderRepository = new OrderRepository();
        actionLogService = new ActionLogService();
    }

    public boolean addCar(Car car) {
        actionLogService.logAction(ActionLog.ActionType.CREATE, "Created car");
        return carRepository.save(car);
    }


    public boolean updateCar(Car car) {
        actionLogService.logAction(ActionLog.ActionType.CREATE, "Updated car");
        return carRepository.update(car);
    }

    public boolean deleteCar(Car car) {
        actionLogService.logAction(ActionLog.ActionType.DELETE, "Deleted car");
        return carRepository.delete(car);
    }

    public Car findCarById(int id) {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "Searching for car");
        Car car = carRepository.findById(id);
        return car;
    }

    public List<Car> getAvailableCars() {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "Get available cars");
        List<Car> list = carRepository.findAllAvailableCars();
        return list;
    }

    public Car getCarById(int id) {
        return carRepository.findById(id);
    }

}
