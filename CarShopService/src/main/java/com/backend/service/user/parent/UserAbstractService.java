package com.backend.service.user.parent;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;

import java.util.List;

public abstract class UserAbstractService {

    // Авторизация
    public abstract boolean register();
    public abstract boolean login(String username, String password);

    // Управление автомобилями
    public abstract List<Car> viewAvailableCars();

    // Обработка заказов
    public abstract boolean createOrder(Car car);
    public abstract List<Order> viewMyOrders();

    // Просмотр информации
    public String getContactInfo(User user) {
        StringBuilder info = new StringBuilder();
        info.append(user.getName() + ":");
        info.append(user.getPhone() + " / ");
        info.append(user.getEmail());

        return info.toString();
    }

    // Фильтрация и поиск
    public abstract List<Car> searchCars(String query);
    public abstract List<Order> searchOrders(String query);

    // Аудит действий пользователя
    public abstract List<ActionLog> viewMyActionLog();

}
