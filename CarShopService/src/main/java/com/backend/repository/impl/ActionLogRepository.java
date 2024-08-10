package com.backend.repository.impl;

import com.backend.model.ActionLog;
import com.backend.model.User;
import com.backend.repository.interfaces.CrudRepository;
import lombok.Setter;

import java.util.*;

public class ActionLogRepository implements CrudRepository<ActionLog> {

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
    public ActionLog findById(int id) {
        return null;
    }

    @Override
    public List<ActionLog> findAll() {
        return null;
    }

    public List<ActionLog> findByUser(User user) {
        List<ActionLog> actionLogs = new ArrayList<>();
//        for (ActionLog actionLog : logs.values()) {
//            if (actionLog.getUser().equals(user)) {
//                result.put(actionLog.getId(), actionLog);
//            }
//        }
        return null;
    }
}
