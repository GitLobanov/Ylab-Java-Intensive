package com.backend.repository;

import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.parent.CrudRepository;

import java.rmi.server.UID;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderRepository implements CrudRepository<Order> {

    private static OrderRepository instance;
    private final Map<UUID, Order> orders;

    public OrderRepository () {
        orders = new HashMap<>();
    }

    public static OrderRepository getInstance () {
        if (instance == null) {
            synchronized (OrderRepository.class) {
                if (instance == null) {
                    instance = new OrderRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean save(Order order) {
        return orders.put(order.getId(), order) != null;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public boolean delete(Order order) {
        return false;
    }

    @Override
    public Order findById(UUID uid) {
        return orders.get(uid);
    }

    @Override
    public Map<UUID, Order> findAll() {
        return orders;
    }

    public Map<UUID, Order> findByType(Order.TypeOrder typeOrder) {
        Map<UUID, Order> result = new HashMap<>();
        for (Order order : orders.values()) {
            if (order.getType() == typeOrder) {
                result.put(order.getId(), order);
            }
        }
        return result;
    }
}
