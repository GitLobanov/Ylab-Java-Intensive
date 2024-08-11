package com.backend.service.impl;

import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.OrderRepository;
import com.backend.repository.impl.UserRepository;
import com.backend.service.EmployeeAbstractService;

import java.util.*;


public class ManagerService extends EmployeeAbstractService {

    public void viewMyClients(User manager) {
       List<Order> orders = orderRepository.findByManager(manager);
       List<User> clients = new ArrayList<>();

        for (Order order : orders) {
            clients.add(order.getClient().getId(), order.getClient());
        }

        displaySearchResultUser(clients.iterator());
    }


    @Override
    public boolean addOrder(Order order) {
        return false;
    }


    public void viewMyTakenOrders(User manager) {
        List<Order> orders = orderRepository.findByManager(manager);
        List<Order> buyingOrders = new ArrayList<>();

        for (Order order : orders) {
            if (order.getType() == Order.TypeOrder.BUYING) {
                buyingOrders.add(order);
            }
        }

        displaySearchResultOrder(buyingOrders.iterator());
    }


    public void viewMyTakenRequests(User manager) {
        List<Order> orders = orderRepository.findByManager(manager);
        List<Order> serviceRequests = new ArrayList<>();

        for (Order order : orders) {
            if (order.getType() == Order.TypeOrder.SERVICE) {
                serviceRequests.add(order);
            }
        }

        displaySearchResultOrder(serviceRequests.iterator());
    }
}
