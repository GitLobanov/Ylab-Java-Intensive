package com.backend.repository.impl;

import com.backend.model.Car;
import com.backend.repository.abstracts.BaseRepository;
import com.backend.repository.interfaces.CrudRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CarRepository extends BaseRepository<Car> {

    @Override
    public boolean save(Car car) {
        String sql = "INSERT INTO car VALUES (?,?,?)";
        return execute(sql, car);
    }

    @Override
    public boolean update(Car car) {
        return false;
    }

    @Override
    public boolean delete(Car car) {
        return false;
    }

    @Override
    public Car findById(int id) {
        return null;
    }

    @Override
    public List<Car> findAll() {
        return null;
    }


    public Map<UUID,Car> findAllAvailableCars() {
        return null;
    }

    @Override
    protected Car mapRowToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
