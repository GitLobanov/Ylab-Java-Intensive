package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionLogService {

    private static List<ActionLog> logEntries = new ArrayList<>();

    public static void log(User user, ActionLog.ActionType actionType, String message) {
        logEntries.add(new ActionLog(user, actionType, LocalDateTime.now(), message));
    }

    public static List<ActionLog> getLogEntries() {
        return new ArrayList<>(logEntries);
    }

    public static List<ActionLog> filterByUser(User user) {
        return logEntries.stream()
                .filter(entry -> entry.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public static List<ActionLog> filterByActionType(ActionLog.ActionType actionType) {
        return logEntries.stream()
                .filter(entry -> entry.getActionType() == actionType)
                .collect(Collectors.toList());
    }

    public static List<ActionLog> filterByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return logEntries.stream()
                .filter(entry -> !entry.getActionDateTime().isBefore(startDate) && !entry.getActionDateTime().isAfter(endDate))
                .collect(Collectors.toList());
    }

    public static void exportToFile(String fileName) {
        // Реализация экспорта в файл
    }

}
