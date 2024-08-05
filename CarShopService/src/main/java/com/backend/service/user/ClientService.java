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

/**
 * Service class for managing client-specific operations.
 * Extends the functionality of UserAbstractService to include viewing personal action logs, cars, and requests.
 */

public class ClientService extends UserAbstractService {

    /**
     * Views the action logs of the current client.
     * Retrieves all action logs from the repository and displays them.
     */

    public void viewMyActionLog() {
        Map<UUID,ActionLog> actionLogMap = ActionLogRepository.getInstance().findAll();
        displaySearchResultActionLog(actionLogMap.entrySet().iterator());
    }

    /**
     * Views the cars associated with the given client.
     * Logs the action and displays the cars owned by the client.
     *
     * @param client the client whose cars are to be viewed
     */

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


    /**
     * Views the service requests made by the given client.
     * Logs the action and displays the service requests of the client.
     *
     * @param client the client whose service requests are to be viewed
     */

    public void viewMyRequests(User client) {
        log(ActionLog.ActionType.VIEW, "View own requests");
        Map<UUID,Order> myRequests = OrderRepository.getInstance().findByClient(client);
        myRequests = myRequests.entrySet().stream()
                .filter(entry -> entry.getValue().getType() == Order.TypeOrder.SERVICE)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));;
        displaySearchResultOrder(myRequests.entrySet().iterator());
    }

    /**
     * Views the purchase orders made by the given client.
     * Logs the action and displays the purchase orders of the client.
     *
     * @param client the client whose purchase orders are to be viewed
     */

    public void viewMyOrders(User client) {
        log(ActionLog.ActionType.VIEW, "View own orders");
        Map<UUID,Order> myRequests = OrderRepository.getInstance().findByClient(client);
        myRequests = myRequests.entrySet().stream()
                .filter(entry -> entry.getValue().getType() == Order.TypeOrder.BUYING)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));;
        displaySearchResultOrder(myRequests.entrySet().iterator());
    }

}
