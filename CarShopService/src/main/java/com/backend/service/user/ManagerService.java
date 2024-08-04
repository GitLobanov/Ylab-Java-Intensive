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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public boolean cancelOrder(Order order) {
        return false;
    }

    public void viewMyClients(User manager) {

        Map<UUID,Order> orders = OrderRepository.getInstance().findByManager(manager);
        Map<UUID,User> clients = new HashMap<>();

        for (Order order : orders.values()) {
            clients.put(order.getClient().getId(), order.getClient());
        }

        displaySearchResultUser(clients.entrySet().iterator());

    }

    @Override
    public boolean addOrder(Order order) {
        return false;
    }

    public void viewMyTakenOrders(User manager) {
        Map<UUID,Order> orders = OrderRepository.getInstance().findByManager(manager);
        Map<UUID,Order> buyingOrders = new HashMap<>();

        for (Order order : orders.values()) {
            if (order.getType() == Order.TypeOrder.BUYING) {
                buyingOrders.put(order.getId(), order);
            }
        }

        displaySearchResultOrder(buyingOrders.entrySet().iterator());
    }

    public void viewMyTakenRequests(User manager) {
        Map<UUID,Order> orders = OrderRepository.getInstance().findByManager(manager);
        Map<UUID,Order> serviceRequests = new HashMap<>();

        for (Order order : orders.values()) {
            if (order.getType() == Order.TypeOrder.SERVICE) {
                serviceRequests.put(order.getId(), order);
            }
        }

        displaySearchResultOrder(serviceRequests.entrySet().iterator());
    }
}
