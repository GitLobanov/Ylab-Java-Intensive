package com.backend.repository.parent;

import java.rmi.server.UID;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CrudRepository <T>{

    abstract boolean save(T t);
    abstract boolean update(T t);
    abstract boolean delete(T t);
    abstract T findById(UUID uid);
    abstract Map<UUID,T> findAll();

}
