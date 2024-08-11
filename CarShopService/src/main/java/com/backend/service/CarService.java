package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.OrderRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarService {

    CarRepository carRepository = new CarRepository();
    OrderRepository orderRepository = new OrderRepository();

    public List<Car> getNotOrderedCars() {
        Iterator<Car> iterator = carRepository.findAllAvailableCars().iterator();
        List<Car> list = new ArrayList<>();
        while (iterator.hasNext()) {
            Car car = iterator.next();
            if (orderRepository.findByCar(car) == null) list.add(car);
        }

        return list;
    }

    public Car getCarById(int id) {
        return carRepository.findById(id);
    }

}
