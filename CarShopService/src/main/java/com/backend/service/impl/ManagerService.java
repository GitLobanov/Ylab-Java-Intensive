package com.backend.service.impl;

import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.OrderRepository;
import com.backend.repository.impl.UserRepository;
import com.backend.service.EmployeeAbstractService;

import java.util.*;


public class ManagerService extends EmployeeAbstractService {

    UserRepository userRepository = new UserRepository();

    public void viewMyClients(User manager) {
//       List<Order> orders = OrderRepository.getInstance().findByManager(manager);
//       List<User> clients = new ArrayList<>();
//
//        for (Order order : orders) {
//            clients.add(order.getClient().getId(), order.getClient());
//        }
//
//        displaySearchResultUser(clients.entrySet().iterator());
    }


    @Override
    public boolean addOrder(Order order) {
        return false;
    }


    public void viewMyTakenOrders(User manager) {
        Map<UUID, Order> orders = OrderRepository.getInstance().findByManager(manager);
        Map<UUID, Order> buyingOrders = new HashMap<>();

        for (Order order : orders.values()) {
            if (order.getType() == Order.TypeOrder.BUYING) {
                buyingOrders.put(order.getId(), order);
            }
        }

        displaySearchResultOrder(buyingOrders.entrySet().iterator());
    }


    public void viewMyTakenRequests(User manager) {
        Map<UUID, Order> orders = OrderRepository.getInstance().findByManager(manager);
        Map<UUID, Order> serviceRequests = new HashMap<>();

        for (Order order : orders.values()) {
            if (order.getType() == Order.TypeOrder.SERVICE) {
                serviceRequests.put(order.getId(), order);
            }
        }

        displaySearchResultOrder(serviceRequests.entrySet().iterator());
    }
}
