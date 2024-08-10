package com.backend.repository.interfaces;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CrudRepository <T> {


    abstract boolean save (T entity);
    abstract boolean update (T entity);
    abstract boolean delete (T entity);
    abstract T findById(int id);
    abstract List<T> findAll();

}
