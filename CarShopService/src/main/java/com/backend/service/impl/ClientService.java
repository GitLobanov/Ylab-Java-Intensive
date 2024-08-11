package com.backend.service.impl;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.ActionLogRepository;
import com.backend.repository.impl.OrderRepository;
import com.backend.service.UserAbstractService;

import java.util.*;
import java.util.stream.Collectors;

public class ClientService extends UserAbstractService {


    public void viewMyActionLog() {
        List<ActionLog> actionLogMap = actionLogRepository.findAll();
        displaySearchResultActionLog(actionLogMap.iterator());
    }


    public void viewMyCars(User client) {
        log(ActionLog.ActionType.VIEW, "View own cars");

        List<Order> orders = orderRepository.findByClient(client);
        List<Car> cars = new ArrayList<>();

        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Car car = iterator.next().getCar();
            cars.add(car);
        }

        displaySearchResultCar(cars.iterator());
    }


    public void viewMyRequests(User client) {
        log(ActionLog.ActionType.VIEW, "View own requests");
         List<Order> myRequests = orderRepository.findByClient(client);
        myRequests = myRequests.stream()
                .filter(entry -> entry.getType() == Order.TypeOrder.SERVICE)
                .toList();;
        displaySearchResultOrder(myRequests.iterator());
    }


    public void viewMyOrders(User client) {
        log(ActionLog.ActionType.VIEW, "View own orders");
        List<Order> myRequests = orderRepository.findByClient(client);
        myRequests = myRequests.stream()
                .filter(entry -> entry.getType() == Order.TypeOrder.BUYING)
                .toList();
        displaySearchResultOrder(myRequests.iterator());
    }

}
