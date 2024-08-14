package com.backend.repository.interfaces;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CrudRepository <T> {

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(T entity);

    T findById(int id);

    List<T> findAll();

}
