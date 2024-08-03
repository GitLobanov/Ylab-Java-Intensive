package com.backend.service.user;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.repository.ActionLogRepository;
import com.backend.repository.CarRepository;
import com.backend.repository.OrderRepository;
import com.backend.repository.UserRepository;
import com.backend.service.user.parent.UserAbstractService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientService extends UserAbstractService {

    private UserRepository userRepository = new UserRepository();
    private OrderRepository orderRepository = new OrderRepository();
    private CarRepository carRepository = new CarRepository();
    private ActionLogRepository actionLogRepository = new ActionLogRepository();

    @Override
    public boolean createOrder(Order order) {
        return false;
    }

    public Map<UUID,Order> viewMyOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Map<UUID,ActionLog> viewMyActionLog() {
        return actionLogRepository.findAll();
    }
}
