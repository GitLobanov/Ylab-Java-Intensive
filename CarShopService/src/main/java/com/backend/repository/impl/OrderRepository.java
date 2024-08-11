package com.backend.repository.impl;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.abstracts.BaseRepository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderRepository extends BaseRepository<Order> {

    @Override
    public boolean save(Order order) {
        String sql = "insert into main.order values(DEFAULT,?,?,?,?,?,?)";
        return execute(sql, order.getId(), order.getCar().getId(), order.getClient().getId(), order.getOrderDate(),
                order.getStatus(), order.getType(), order.getNote(), order.getManager().getId());
    }

    @Override
    public boolean update(Order order) {
        String sql = "UPDATE main.order SET car = ?, client = ?, orderDateTime = ?, " +
                "status = ?, type = ?, note = ?, manager = ? WHERE id = ?";
        return execute(sql, order.getCar().getId(), order.getClient().getId(), order.getOrderDate(),
                order.getStatus(), order.getType(), order.getNote(), order.getManager().getId(), order.getId());
    }

    @Override
    public boolean delete(Order order) {
        String sql = "DELETE FROM main.order WHERE id = ?";
        return execute(sql, order.getId());
    }

    @Override
    public Order findById(int id) {
        String sql = "SELECT * FROM main.order WHERE id = ?";
        return findById(sql, id);
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM main.order";
        return findAll(sql);
    }

    public List<Order> findByType(Order.TypeOrder typeOrder) {
        String sql = "SELECT * FROM main.user WHERE type = ?";
        return findBy(sql, typeOrder.name());
    }


    public List<Order> findByClient(User client) {
        String sql = "SELECT * FROM main.user WHERE client = ?";
        return findBy(sql, client.getId());
    }

    public List<Order> findByCar(Car car) {
        String sql = "SELECT * FROM main.user WHERE car = ?";
        return findBy(sql, car.getId());
    }


    public List<Order> findByManager(User manager) {
        String sql = "SELECT * FROM main.user WHERE manager = ?";
        return findBy(sql, manager.getId());
    }

    @Override
    protected Order mapRowToEntity(ResultSet rs) throws SQLException {
        CarRepository carRepository = new CarRepository();
        UserRepository userRepository = new UserRepository();

        int id = rs.getInt("id");
        Car car = carRepository.findById(rs.getInt("car"));
        User client = userRepository.findById(rs.getInt("client"));
        User manager = userRepository.findById(rs.getInt("manager"));
        Date orderDateTime = Date.valueOf(rs.getString("orderDateTime"));
        Order.OrderStatus status = Order.OrderStatus.valueOf(rs.getString("status").toUpperCase());
        Order.TypeOrder type = Order.TypeOrder.valueOf(rs.getString("type").toUpperCase());
        String note = rs.getString("note");
        return new Order(id, car, client, orderDateTime, status, type, note, manager);
    }
}
