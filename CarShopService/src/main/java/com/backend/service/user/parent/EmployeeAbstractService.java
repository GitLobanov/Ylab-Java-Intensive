package com.backend.service.user.parent;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class EmployeeAbstractService extends UserAbstractService {

    // Управление автомобилями
    public abstract boolean addCar(Car car);
    public abstract boolean updateCar(Car car);
    public abstract boolean deleteCar(Car car);

    // Обработка заказов
    public abstract Map<UUID, Order> viewAllOrders();
    public abstract boolean updateOrderStatus(Order order, Order.OrderStatus status);
    public abstract boolean cancelOrder(Order order);

    // Просмотр информации о клиентах и сотрудниках
    public abstract Map<UUID,User> viewMyClients();
    public abstract boolean addEmployee(User employee);
    public abstract boolean updateEmployee(User employee);

}
