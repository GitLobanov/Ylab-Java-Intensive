package com.backend.service.user;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.service.user.parent.EmployeeAbstractService;

import java.util.List;

public class ManagerService extends EmployeeAbstractService {
    @Override
    public boolean addCar(Car car) {
        return false;
    }

    @Override
    public boolean updateCar(Car car) {
        return false;
    }

    @Override
    public boolean deleteCar(Car car) {
        return false;
    }

    @Override
    public List<Order> viewAllOrders() {
        return List.of();
    }

    @Override
    public boolean updateOrderStatus(Order order, Order.OrderStatus status) {
        return false;
    }

    @Override
    public boolean cancelOrder(Order order) {
        return false;
    }

    @Override
    public List<User> viewMyClients() {
        return List.of();
    }

    @Override
    public boolean addEmployee(User employee) {
        return false;
    }

    @Override
    public boolean updateEmployee(User employee) {
        return false;
    }

    @Override
    public boolean register() {
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public List<Car> viewAvailableCars() {
        return List.of();
    }

    @Override
    public boolean createOrder(Car car) {
        return false;
    }

    @Override
    public List<Order> viewMyOrders() {
        return List.of();
    }

    @Override
    public List<Car> searchCars(String query) {
        return List.of();
    }

    @Override
    public List<Order> searchOrders(String query) {
        return List.of();
    }

    @Override
    public List<ActionLog> viewMyActionLog() {
        return List.of();
    }
}
