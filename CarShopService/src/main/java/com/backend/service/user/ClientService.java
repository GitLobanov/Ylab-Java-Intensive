package com.backend.service.user;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.ActionLogRepository;
import com.backend.repository.CarRepository;
import com.backend.repository.OrderRepository;
import com.backend.repository.UserRepository;
import com.backend.service.user.parent.UserAbstractService;
import com.backend.util.Session;

import java.util.*;
import java.util.stream.Collectors;

public class ClientService extends UserAbstractService {

    public void viewMyActionLog() {
        Map<UUID,ActionLog> actionLogMap = ActionLogRepository.getInstance().findAll();
        displaySearchResultActionLog(actionLogMap.entrySet().iterator());
    }

    public void viewMyCars(User client) {
        log(ActionLog.ActionType.VIEW, "View own cars");

        Map<UUID, Order> orders = OrderRepository.getInstance().findByClient(client);
        Map<UUID, Car> cars = new HashMap<>();

        Iterator<Map.Entry<UUID, Order>> iterator = orders.entrySet().iterator();
        while (iterator.hasNext()) {
            Car car = iterator.next().getValue().getCar();
            cars.put(car.getId(), car);
        }

        displaySearchResultCar(cars.entrySet().iterator());
    }

    public void viewMyRequests(User client) {
        log(ActionLog.ActionType.VIEW, "View own requests");
        Map<UUID,Order> myRequests = OrderRepository.getInstance().findByClient(client);
        myRequests = myRequests.entrySet().stream()
                .filter(entry -> entry.getValue().getType() == Order.TypeOrder.SERVICE)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));;
        displaySearchResultOrder(myRequests.entrySet().iterator());
    }


    public void viewMyOrders(User client) {
        log(ActionLog.ActionType.VIEW, "View own orders");
        Map<UUID,Order> myRequests = OrderRepository.getInstance().findByClient(client);
        myRequests = myRequests.entrySet().stream()
                .filter(entry -> entry.getValue().getType() == Order.TypeOrder.BUYING)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));;
        displaySearchResultOrder(myRequests.entrySet().iterator());
    }

}
