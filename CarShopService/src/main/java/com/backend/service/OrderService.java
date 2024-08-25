package com.backend.service;

import com.backend.dto.ClientDTO;
import com.backend.dto.OrderDTO;
import com.backend.mapper.ClientMapper;
import com.backend.mapper.OrderMapper;
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

    OrderMapper orderMapper;
    ClientMapper clientMapper;

    public OrderService() {
        orderRepository  = new OrderRepository();
        userRepository = new UserRepository();

        orderMapper = OrderMapper.INSTANCE;
        clientMapper = ClientMapper.INSTANCE;
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

    public List<OrderDTO> getClientRequests(String username) {
        List<Order> orders = orderRepository.findRequestsByClient(username);
        return orderMapper.getDTOs(orders);
    }


    public List<OrderDTO> getClientOrders(String username) {
        List<Order> orders = orderRepository.findOrdersByClient(username);
        return orderMapper.getDTOs(orders);
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

    public List<ClientDTO> getClientsByManager(String manager) {
        List<User> users = userRepository.findClientsByManager(manager);
        return clientMapper.getDTOs(users);
    }

    public int getClientOrderCount(User client) {
        return orderRepository.findOrdersByClient(client.getUsername()).size();
    }

    public int getClientServiceRequestCount(User client) {
        return orderRepository.findRequestsByClient(client.getUsername()).size();
    }

    public List<OrderDTO> getAllBuyingOrders (){
        List<Order> orders = orderRepository.findByType(Order.TypeOrder.BUYING);
        return orderMapper.getDTOs(orders);

    }

    public List<OrderDTO> getAllServiceOrders() {
        List<Order> orders = orderRepository.findByType(Order.TypeOrder.SERVICE);
        return orderMapper.getDTOs(orders);
    }

    public List<OrderDTO> getOrdersBySearch(OrderDTO order) {
        List<Order> orders = orderRepository.search(order);
        return orderMapper.getDTOs(orders);
    }

}
