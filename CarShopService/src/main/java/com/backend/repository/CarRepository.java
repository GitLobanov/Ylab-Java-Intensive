package com.backend.repository;

import com.backend.model.Car;
import com.backend.repository.parent.CrudRepository;

import java.util.List;

public class CarRepository implements CrudRepository<Car> {
    @Override
    public void save(Car car) {

    }

    @Override
    public void update(Car car) {

    }

    @Override
    public void delete(Car car) {

    }

    @Override
    public Car find(Car car) {
        return null;
    }

    @Override
    public List<Car> findAll() {
        return List.of();
    }
}
