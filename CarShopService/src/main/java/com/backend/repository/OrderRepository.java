package com.backend.repository;

import com.backend.model.Order;
import com.backend.repository.parent.CrudRepository;

import java.util.List;

public class OrderRepository implements CrudRepository<Order> {
    @Override
    public void save(Order order) {

    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public Order find(Order order) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }
}
