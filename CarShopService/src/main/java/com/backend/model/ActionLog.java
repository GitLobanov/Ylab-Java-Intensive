package com.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ActionLog {

    private User user;
    private ActionType actionType ;
    private LocalDateTime actionDateTime;
    private String message;

    public enum ActionType {
        CREATE, UPDATE, DELETE, LOGIN, LOGOUT
    }

}
