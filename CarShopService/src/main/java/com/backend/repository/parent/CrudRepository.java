package com.backend.repository.parent;

import java.util.List;

public interface CrudRepository <T>{

    abstract void save(T t);
    abstract void update(T t);
    abstract void delete(T t);
    abstract T find(T t);
    abstract List<T> findAll();

}
