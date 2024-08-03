package com.backend.service.user;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.ActionLogRepository;
import com.backend.repository.CarRepository;
import com.backend.repository.OrderRepository;
import com.backend.repository.UserRepository;
import com.backend.service.user.parent.EmployeeAbstractService;

import java.util.*;
import java.util.stream.Collectors;

public class AdminService extends EmployeeAbstractService {

    @Override
    public boolean addCar(Car car) {
        return CarRepository.getInstance().save(car);
    }

    @Override
    public boolean updateCar(Car car) {
        return CarRepository.getInstance().update(car);
    }

    @Override
    public boolean deleteCar(Car car) {
        return CarRepository.getInstance().delete(car);
    }

    public Car findCarById(UUID id) {
        Car car = CarRepository.getInstance().findById(id);
        return car;
    }

    @Override
    public Map<UUID,User> viewMyClients() {
        return UserRepository.getInstance().findAll();
    }

    public boolean addEmployee(User employee) {
        return  UserRepository.getInstance().save(employee);
    }

    public boolean updateEmployee(User employee) {
        return UserRepository.getInstance().save(employee);
    }

    @Override
    public Map<UUID, Order> viewMyOrders() {
        return OrderRepository.getInstance().findAll();
    }

    @Override
    public Map<UUID, Order> searchOrders(String query) {
        return OrderRepository.getInstance().findAll();
    }

    @Override
    public Map<UUID, ActionLog> viewMyActionLog() {
        return ActionLogRepository.getInstance().findAll();
    }

}
