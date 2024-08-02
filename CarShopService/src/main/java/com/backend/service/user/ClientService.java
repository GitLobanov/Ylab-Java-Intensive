package com.backend.service.user;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.service.user.parent.UserAbstractService;

import java.util.List;

public class ClientService extends UserAbstractService {
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
