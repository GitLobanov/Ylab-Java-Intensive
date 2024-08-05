package com.backend.service;

import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.OrderRepository;
import com.backend.service.parent.EmployeeAbstractService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service class for handling operations specific to managers.
 */
public class ManagerService extends EmployeeAbstractService {

    /**
     * Displays a list of clients managed by the specified manager.
     *
     * @param manager the manager whose clients are to be viewed
     */
    public void viewMyClients(User manager) {
        Map<UUID, Order> orders = OrderRepository.getInstance().findByManager(manager);
        Map<UUID, User> clients = new HashMap<>();

        for (Order order : orders.values()) {
            clients.put(order.getClient().getId(), order.getClient());
        }

        displaySearchResultUser(clients.entrySet().iterator());
    }

    /**
     * Adds a new order.
     *
     * @param order the order to be added
     * @return {@code false} as this method is not implemented for managers
     */
    @Override
    public boolean addOrder(Order order) {
        return false;
    }

    /**
     * Displays a list of buying orders taken by the specified manager.
     *
     * @param manager the manager whose taken buying orders are to be viewed
     */
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

    /**
     * Displays a list of service requests taken by the specified manager.
     *
     * @param manager the manager whose taken service requests are to be viewed
     */
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
