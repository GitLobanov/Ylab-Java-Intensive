package com.backend.service.user.parent;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.OrderRepository;
import com.backend.repository.UserRepository;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public abstract class EmployeeAbstractService extends UserAbstractService {

    // Управление автомобилями
    public abstract boolean addCar(Car car);
    public abstract boolean updateCar(Car car);
    public abstract boolean deleteCar(Car car);

    // Обработка заказов
    public void viewAllOrders() {
        Iterator<Map.Entry<UUID, Order>> iterator = OrderRepository.getInstance().findAll().entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getValue());
        }
    }

    public boolean updateOrderStatus(Order order, Order.OrderStatus status) {
        order.setStatus(status);
        return OrderRepository.getInstance().update(order);
    }

    public boolean cancelOrder(Order order) {
        order.setStatus(Order.OrderStatus.CANCELLED);
        return OrderRepository.getInstance().update(order);
    }

    // Просмотр информации о клиентах и сотрудниках
    public void viewAllClients () {
        Iterator<Map.Entry<UUID, User>> iterator = UserRepository.getInstance().findAll().entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getValue());
        }
    }
    public abstract Map<UUID,User> viewMyClients();

}
