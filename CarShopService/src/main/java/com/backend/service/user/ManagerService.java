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

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ManagerService extends EmployeeAbstractService {

    private UserRepository userRepository = new UserRepository();
    private CarRepository carRepository = new CarRepository();
    private OrderRepository orderRepository = new OrderRepository();
    private ActionLogRepository actionLogRepository = new ActionLogRepository();

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
    public boolean updateOrderStatus(Order order, Order.OrderStatus status) {
        return false;
    }

    @Override
    public boolean cancelOrder(Order order) {
        return false;
    }

    public Map<UUID,User> viewMyClients() {
        return userRepository.findAll();
    }

    @Override
    public boolean createOrder(Order order) {
        return false;
    }

    public Map<UUID,Order> viewMyOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Map<UUID,Car> searchCars(String query) {
        return carRepository.findAll();
    }

    @Override
    public Map<UUID,Order> searchOrders(String query) {
        return orderRepository.findAll();
    }

    @Override
    public Map<UUID,ActionLog> viewMyActionLog() {
        return actionLogRepository.findAll();
    }
}
