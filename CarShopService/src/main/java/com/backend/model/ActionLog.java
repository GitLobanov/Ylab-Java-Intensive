package com.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class ActionLog {

    private UUID id;
    private User user;
    private ActionType actionType ;
    private LocalDateTime actionDateTime;
    private String message;

    public enum ActionType {
        CREATE, UPDATE, DELETE, LOGIN, LOGOUT
    }

    public ActionLog(User user, ActionType actionType, String message) {
        setId(UUID.randomUUID());
        setUser(user);
        setActionType(actionType);
        setActionDateTime(LocalDateTime.now());
        setMessage(message);
    }
}
