package com.backend.service;

import com.backend.dto.CarDTO;
import com.backend.mapper.CarMapper;
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
    private final CarMapper carMapper;

    public CarService () {
        carRepository = new CarRepository();
        orderRepository = new OrderRepository();
        this.carMapper = CarMapper.INSTANCE;
    }

    public boolean addCar(CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);
        return carRepository.save(car);
    }


    public boolean updateCar(CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);
        return carRepository.update(car);
    }

    public boolean deleteCar(int id) {
        return carRepository.delete(findCarById(id));
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

    public List<CarDTO> getAllCars() {
        return carMapper.getDTOs(carRepository.findAll());
    }


}
