package com.backend.repository;

import com.backend.model.Car;
import com.backend.model.User;
import com.backend.repository.parent.CrudRepository;

import java.rmi.server.UID;
import java.util.*;

public class CarRepository implements CrudRepository<Car> {

    private static CarRepository instance;
    private final Map<UUID, Car> cars;

    public CarRepository() {
        cars = new HashMap<>();
    }

    public static CarRepository getInstance() {
        if (instance == null) {
            synchronized (CarRepository.class) {
                if (instance == null) {
                    instance = new CarRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean save(Car car) {
        if (car != null) {
            cars.put(car.getId(), car);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Car car) {
        return false;
    }

    @Override
    public boolean delete(Car car) {
        return cars.remove(car.getId(), car);
    }

    @Override
    public Car findById(UUID uid) {
        return  cars.get(uid);
    }

    @Override
    public Map<UUID,Car> findAll() {
        return cars;
    }


    public Map<UUID,Car> findAllAvailableCars() {
        Map<UUID,Car> availableCars = new HashMap<>();
        Iterator<Map.Entry<UUID,Car>> iterator = cars.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID,Car> entry = iterator.next();
            Car car = entry.getValue();
            if (car.isAvailability()) {
                availableCars.put(car.getId(), car);
            }
        }
        return availableCars;
    }
}
