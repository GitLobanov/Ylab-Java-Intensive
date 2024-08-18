package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.User;
import com.backend.repository.impl.ActionLogRepository;
import com.backend.util.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ActionLogService {

    private ActionLogRepository actionLogRepository;
    private static final Logger logger = LoggerFactory.getLogger("UserActions");

    public ActionLogService() {
        actionLogRepository = new ActionLogRepository();
    }

    public void logAction(ActionLog.ActionType type, String message) {
        Session session = Session.getInstance();
        if (session.getUser() == null) return;
        ActionLog log = new ActionLog(0, session.getUser(),type, Date.valueOf(LocalDate.now().toString()), message);
        logger.info("User: {}, Action: {}, Details: {}", log.getUser().getUsername(), log.getActionType(), log.getMessage());
        actionLogRepository.save(log);
    }

    public List<ActionLog> getUserActionLog(User user) {
        return actionLogRepository.findByUser(user);
    }



    public void getActionLogByUserSearch(String query, User user) {
        List<ActionLog> logs = user == null ? actionLogRepository.findAll() : actionLogRepository.findByUser(user);
        String[] params = query.split(";");

        for (String param : params) {
            String[] keyValue = param.split(":");

            if (keyValue.length < 2) {
                continue;
            }

            String key = keyValue[0];
            String value = keyValue[1];

            switch (key) {
                case "actionType":
                    logs = logs.stream()
                            .filter(log -> log.getActionType().toString().equalsIgnoreCase(value))
                            .collect(Collectors.toList());
                    break;
                case "actionDateTimeFrom<actionDateTime<actionDateTimeTo":
                    String[] dateTimeRange = value.split("<actionDateTime<");
                    LocalDateTime dateTimeFrom = LocalDateTime.parse(dateTimeRange[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDateTime dateTimeTo = LocalDateTime.parse(dateTimeRange[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    logs = logs.stream()
                            .filter(log -> log.getActionDateTime().toLocalDate().isAfter(dateTimeFrom.toLocalDate()) && log.getActionDateTime().toLocalDate().isBefore(dateTimeTo.toLocalDate()))
                            .collect(Collectors.toList());
                    break;
                case "actionDateTimeFrom<actionDateTime":
                    LocalDateTime dateTimeOnlyFrom = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    logs = logs.stream()
                            .filter(log -> log.getActionDateTime().toLocalDate().isAfter(dateTimeOnlyFrom.toLocalDate()))
                            .collect(Collectors.toList());
                    break;
                case "actionDateTime<actionDateTimeTo":
                    LocalDateTime dateTimeOnlyTo = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    logs = logs.stream()
                            .filter(log -> log.getActionDateTime().toLocalDate().isBefore(dateTimeOnlyTo.toLocalDate()))
                            .collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Forming query from user's input in console
     */

    public String formingQuerySearchActionLogs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input parameters (if field doesn't need leave it blank)");

        System.out.println("Action Type (CREATE/UPDATE/DELETE/CANCEL/VIEW/LOGIN/LOGOUT): ");
        String actionType = scanner.nextLine().trim();

        System.out.println("Action DateTime From (yyyy-MM-dd HH:mm:ss): ");
        String dateTimeFrom = scanner.nextLine().trim();

        System.out.println("Action DateTime To (yyyy-MM-dd HH:mm:ss): ");
        String dateTimeTo = scanner.nextLine().trim();

        StringBuilder queryBuilder = new StringBuilder();

        if (!actionType.isEmpty()) {
            queryBuilder.append("actionType:").append(actionType).append(";");
        }

        if (!dateTimeFrom.isEmpty() && !dateTimeTo.isEmpty()) {
            queryBuilder.append("actionDateTimeFrom<actionDateTime<actionDateTimeTo:")
                    .append(dateTimeFrom).append("<actionDateTime<").append(dateTimeTo).append(";");
        } else if (!dateTimeFrom.isEmpty()) {
            queryBuilder.append("actionDateTimeFrom<actionDateTime:").append(dateTimeFrom).append(";");
        } else if (!dateTimeTo.isEmpty()) {
            queryBuilder.append("actionDateTime<actionDateTimeTo:").append(dateTimeTo).append(";");
        }

        String query = queryBuilder.length() > 0 ? queryBuilder.substring(0, queryBuilder.length() - 1) : "";

        return query;
    }

}
