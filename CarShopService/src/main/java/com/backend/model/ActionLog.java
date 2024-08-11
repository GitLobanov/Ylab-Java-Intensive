package com.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ActionLog {

    private UUID id;
    private User user;
    private ActionType actionType ;
    private LocalDateTime actionDateTime;
    private String message;

    public enum ActionType {
        CREATE, UPDATE, DELETE, CANCEL, VIEW, LOGIN, LOGOUT
    }

    public ActionLog(User user, ActionType actionType, String message) {
        setId(UUID.randomUUID());
        setUser(user);
        setActionType(actionType);
        setActionDateTime(LocalDateTime.now());
        setMessage(message);
    }

    @Override
    public String toString() {
        return "ActionLog{" +
                "user=" + user.getUsername() +
                ", actionType= [" + actionType +
                "], actionDateTime=" + actionDateTime +
                ", message='" + message + '\'' +
                '}';
    }
}
