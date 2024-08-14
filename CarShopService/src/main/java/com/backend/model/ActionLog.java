package com.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ActionLog {

    private int id;
    private User user;
    private ActionType actionType ;
    private Date actionDateTime;
    private String message;

    public enum ActionType {
        CREATE, UPDATE, DELETE, CANCEL, VIEW, LOGIN, LOGOUT
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
