package com.backend.repository.impl;

import com.backend.dto.OrderDTO;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.abstracts.BaseRepository;
import com.backend.util.db.SQLRequest;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository extends BaseRepository<Order> {

    @Override
    public boolean save(Order order) {
        String sql = SQLRequest.INSERT_ALL_INTO_ORDER;
        return execute(sql, order.getCar().getId(), order.getClient().getId(), java.sql.Date.valueOf(order.getOrderDate().toLocalDate()),
                order.getStatus().name(), order.getType().name(), order.getNote(), order.getManager().getId());
    }

    @Override
    public boolean update(Order order) {
        String sql = "UPDATE main.order SET car = ?, client = ?, \"orderDateTime\" = ?, " +
                "status = ?, type = ?, note = ?, manager = ? WHERE id = ?";
        return execute(sql, order.getCar().getId(), order.getClient().getId(), java.sql.Date.valueOf(order.getOrderDate().toLocalDate()),
                order.getStatus().name(), order.getType().name(), order.getNote(), order.getManager().getId(), order.getId());
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
        String sql = "SELECT * FROM main.order WHERE type = ?";
        return findBy(sql, typeOrder.name());
    }


    public List<Order> findOrdersByClient(String username) {
        String sql = """
                SELECT o.id,
                       o.car,
                       o.client,
                       o."orderDateTime",
                       o.status,
                       o.type,
                       o.note,
                       o.manager
                FROM main."order" o
                         JOIN main."user" u ON o.client = u.id
                WHERE o.type = 'BUYING' and u.username = ?;
                """;
        return findBy(sql, username);
    }

    public List<Order> findRequestsByClient(String username) {
        String sql = """
                SELECT o.id,
                       o.car,
                       o.client,
                       o."orderDateTime",
                       o.status,
                       o.type,
                       o.note,
                       o.manager
                FROM main."order" o
                         JOIN main."user" u ON o.client = u.id
                WHERE o.type = 'SERVICE' and u.username = ?;
                """;
        return findBy(sql, username);
    }

    public List<Order> findByCar(Car car) {
        String sql = "SELECT * FROM main.order WHERE car = ?";
        return findBy(sql, car.getId());
    }


    public List<Order> findByManager(User manager) {
        String sql = "SELECT * FROM main.order WHERE manager = ?";
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

    public List<Order> search(OrderDTO order) {
        StringBuilder sql = new StringBuilder("SELECT * FROM main.\"order\" o WHERE 1=1");

        List<Object> parameters = new ArrayList<>();

        if (order.getCar() != null) {
            sql.append(" AND o.car = ?");
            parameters.add(order.getCar().getId());
        }
        if (order.getClient() != null) {
            sql.append(" AND o.client = ?");
            parameters.add(order.getClient().getId());
        }
        if (order.getOrderDate() != null) {
            sql.append(" AND o.orderDate = ?");
            parameters.add(new java.sql.Date(order.getOrderDate().getTime()));
        }
        if (order.getStatus() != null) {
            sql.append(" AND o.status = ?");
            parameters.add(order.getStatus().name());
        }
        if (order.getType() != null) {
            sql.append(" AND o.type = ?");
            parameters.add(order.getType().name());
        }
        if (order.getNote() != null && !order.getNote().isEmpty()) {
            sql.append(" AND o.note LIKE ?");
            parameters.add("%" + order.getNote() + "%");
        }
        if (order.getManager() != null) {
            sql.append(" AND o.manager = ?");
            parameters.add(order.getManager().getId());
        }

        return findBy(sql.toString(), parameters.toArray());
    }
}
