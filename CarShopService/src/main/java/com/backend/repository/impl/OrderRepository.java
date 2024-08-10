package com.backend.repository.impl;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.abstracts.BaseRepository;
import com.backend.repository.interfaces.CrudRepository;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderRepository extends BaseRepository<Order> {

    @Override
    public boolean save(Order order) {
        return false;
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
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    public List<Order> findByType(Order.TypeOrder typeOrder) {
        return null;
    }


    public List<Order> findByClient(User client) {
        return null;
    }

    public Map<UUID, Order> findByCar(Car car) {
        return null;
    }


    public List<Order> findByManager(User manager) {
        return null;
    }

    @Override
    protected Order mapRowToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
