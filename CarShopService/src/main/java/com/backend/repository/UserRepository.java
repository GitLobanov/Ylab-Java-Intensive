package com.backend.repository;

import com.backend.repository.parent.CrudRepository;

import java.util.List;

public class UserRepository implements CrudRepository {
    @Override
    public void save(Object object) {

    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void delete(Object object) {

    }

    @Override
    public Object find(Object object) {
        return null;
    }

    @Override
    public List findAll() {
        return List.of();
    }
}
