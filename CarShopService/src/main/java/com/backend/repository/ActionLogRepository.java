package com.backend.repository;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.User;
import com.backend.repository.parent.CrudRepository;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActionLogRepository implements CrudRepository<ActionLog> {

    @Setter
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
        if (actionLog != null) {
            logs.put(actionLog.getId(), actionLog);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(ActionLog actionLog) {
        return logs.put(actionLog.getId(), actionLog) != null;
    }

    @Override
    public boolean delete(ActionLog actionLog) {
        return logs.remove(actionLog.getId(), actionLog);
    }

    @Override
    public ActionLog findById(UUID uid) {
        return logs.get(uid);
    }

    @Override
    public Map<UUID, ActionLog> findAll() {
        return logs;
    }

    public Map<UUID, ActionLog> findByUser(User user) {
        Map<UUID, ActionLog> result = new HashMap<>();
        for (ActionLog actionLog : logs.values()) {
            if (actionLog.getUser().equals(user)) {
                result.put(actionLog.getId(), actionLog);
            }
        }
        return result;
    }
}
