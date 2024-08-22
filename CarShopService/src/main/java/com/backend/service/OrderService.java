package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.OrderRepository;
import com.backend.repository.impl.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    OrderRepository orderRepository;
    CarRepository carRepository;
    UserRepository userRepository;

    public OrderService() {
        orderRepository  = new OrderRepository();
        userRepository = new UserRepository();
    }

    public boolean addOrder(Order order) {
        checkCarForAvailability(order);
        return orderRepository.save(order);
    }


    public boolean updateOrder(Order order) {
        checkCarForAvailability(order);
        return orderRepository.update(order);
    }


    public boolean deleteOrder(Order order) {
        checkCarForAvailability(order);
        return orderRepository.delete(order);
    }


    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id) == null ?
                Optional.empty() : Optional.of(orderRepository.findById(id));
    }

    public List<Order> getClientRequests(String username) {
        return orderRepository.findRequestsByClient(username);
    }


    public List<Order> getClientOrders(String username) {
        return orderRepository.findOrdersByClient(username);
    }

    private void checkCarForAvailability(Order order) {
        if (order.getType() == Order.TypeOrder.BUYING && order.getStatus() == Order.OrderStatus.COMPLETED) {
            Car car = order.getCar();
            car.setAvailability(false);
            carRepository.save(car);
        }
    }


    public boolean cancelOrder(Order order) {
        order.setStatus(Order.OrderStatus.CANCELLED);
        return orderRepository.update(order);
    }

    public List<User> getClientsByManager(User manager) {
        return userRepository.findClientsByManager(manager);
    }

    public int getClientOrderCount(User client) {
        return orderRepository.findOrdersByClient(client.getUsername()).size();
    }

    public int getClientServiceRequestCount(User client) {
        return orderRepository.findRequestsByClient(client.getUsername()).size();
    }

    public List<Order> getAllBuyingOrders (){
        return orderRepository.findByType(Order.TypeOrder.BUYING);
    }

    public List<Order> getAllServiceOrders() {
        return orderRepository.findByType(Order.TypeOrder.SERVICE);
    }

    public List<Order> getManagerOrders (User manager){
        return orderRepository.findByManager(manager);
    }

    public List<Order> getOrdersBySearch(Order order) {
        return orderRepository.search(order);

    }


}
