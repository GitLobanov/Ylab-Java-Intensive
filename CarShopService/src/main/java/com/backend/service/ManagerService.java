package com.backend.service;

import com.backend.dto.ClientDTO;
import com.backend.dto.OrderDTO;
import com.backend.mapper.ClientMapper;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ManagerService {

    OrderService orderService;
    OrderRepository orderRepository;

    ClientMapper clientMapper;

    public ManagerService() {
        orderService = new OrderService();
        orderRepository = new OrderRepository();
        clientMapper = ClientMapper.INSTANCE;
    }

    public List<ClientDTO> getManagerClients(User manager) {
       List<ClientDTO> clients = orderService.getClientsByManager(manager.getUsername());
        return clients;
    }


    public List<Order> getManagerTakenOrders(User manager) {
        OrderDTO orderDTO  = new OrderDTO();
        orderDTO.setManager(manager);
        orderDTO.setType(Order.TypeOrder.BUYING);
        List<Order> orders = orderRepository.search(orderDTO);
        return orders;
    }


    public List<Order> getManagerTakenRequests(User manager) {
        OrderDTO orderDTO  = new OrderDTO();
        orderDTO.setManager(manager);
        orderDTO.setType(Order.TypeOrder.SERVICE);
        List<Order> orders = orderRepository.search(orderDTO);
        return orders;
    }
}
