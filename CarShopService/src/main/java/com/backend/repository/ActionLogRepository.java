package com.backend.repository;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.repository.parent.CrudRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActionLogRepository implements CrudRepository<ActionLog> {

    private static ActionLogRepository instance;
    private final Map<UUID, ActionLog> logs;

    public ActionLogRepository() {
        logs = new HashMap<>();
    }

    public static ActionLogRepository getInstance() {
        if (instance == null) {
            synchronized (ActionLogRepository.class) {
                if (instance == null) {
                    instance = new ActionLogRepository();
                }
            }
        }
        return instance;
    }


    @Override
    public boolean save(ActionLog actionLog) {
        return false;
    }

    @Override
    public boolean update(ActionLog actionLog) {
        return false;
    }

    @Override
    public boolean delete(ActionLog actionLog) {
        return false;
    }

    @Override
    public ActionLog findById(UUID uid) {
        return null;
    }

    @Override
    public Map<UUID, ActionLog> findAll() {
        return logs;
    }
}
