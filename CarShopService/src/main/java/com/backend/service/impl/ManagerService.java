package com.backend.service.impl;

import com.backend.model.Order;
import com.backend.model.User;
import com.backend.service.OrderService;

import java.util.*;


public class ManagerService {

    OrderService orderService;

    public ManagerService() {
        orderService = new OrderService();
    }

    public List<User> getManagerClients(User manager) {
       List<Order> orders = orderService.getManagerOrders(manager);
       List<User> clients = new ArrayList<>();

        for (Order order : orders) {
            clients.add(order.getClient().getId(), order.getClient());
        }

        return clients;
    }


    public List<Order> getManagerTakenOrders(User manager) {
        List<Order> orders = orderService.getManagerOrders(manager);
        List<Order> buyingOrders = new ArrayList<>();

        for (Order order : orders) {
            if (order.getType() == Order.TypeOrder.BUYING) {
                buyingOrders.add(order);
            }
        }

        return buyingOrders;
    }


    public List<Order> getManagerTakenRequests(User manager) {
        List<Order> orders = orderService.getManagerOrders(manager);
        List<Order> serviceRequests = new ArrayList<>();

        for (Order order : orders) {
            if (order.getType() == Order.TypeOrder.SERVICE) {
                serviceRequests.add(order);
            }
        }

        return  serviceRequests;
    }
}
