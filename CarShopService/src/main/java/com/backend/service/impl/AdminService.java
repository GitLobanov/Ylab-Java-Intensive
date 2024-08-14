package com.backend.service.impl;

import com.backend.model.ActionLog;
import com.backend.model.User;
import com.backend.repository.impl.UserRepository;
import com.backend.service.EmployeeAbstractService;
import com.backend.util.ConsoleColors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class AdminService extends EmployeeAbstractService {

    public void viewAllEmployees() {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "View all employees");
        for (User user : userRepository.findAll()) {
            if (user.getRole() == User.Role.ADMIN || user.getRole() == User.Role.MANAGER) {
                System.out.println(ConsoleColors.PURPLE_BOLD + user + ConsoleColors.RESET);
            }
        }
    }

    public void viewAllClients () {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "View all clients");

        for (User user : userRepository.findAll()) {
            if (user.getRole() == User.Role.CLIENT) {
                System.out.println(ConsoleColors.PURPLE_BOLD + user + ConsoleColors.RESET);
            }
        }
    }


    public boolean addEmployee(User employee) {
        actionLogService.logAction(ActionLog.ActionType.CREATE, "Created employee: " + employee.getUsername());
        return userRepository.save(employee);
    }


    public boolean updateEmployee(User updatedEmployee) {
        if (updatedEmployee==null) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "Employee not found." + ConsoleColors.RESET);
            return false;
        }
        actionLogService.logAction(ActionLog.ActionType.UPDATE, "Updated employee: " + updatedEmployee.getUsername());
        return userRepository.update(updatedEmployee);
    }

    public boolean removeEmployee(String userName) {
        if (userRepository.findByUserName(userName)==null){
            System.out.println(ConsoleColors.YELLOW_BOLD + "Employee not found." + ConsoleColors.RESET);
            return false;
        }
        User employee = userRepository.findByUserName(userName);
        actionLogService.logAction(ActionLog.ActionType.DELETE, "Deleted employee: " + employee.getUsername());
        return userRepository.delete(employee);
    }


    public void searchActionLog(String query) {
        List<ActionLog> logs = actionLogRepository.findAll();
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
                case "user":
                    logs = logs.stream()
                            .filter(log -> log.getUser().getUsername().equalsIgnoreCase(value))
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

        Iterator<ActionLog> iterator = logs.iterator();
        displaySearchResultActionLog(iterator);

        askUnloadActionLogToTXT(logs);
    }

    public String formingQuerySearchActionLogs() {

        System.out.println("Input parameters (if field doesn't need leave it blank)");

        System.out.println("Action Type (CREATE/UPDATE/DELETE/LOGIN/LOGOUT): ");
        String actionType = scanner.nextLine().trim();

        System.out.println("User (username): ");
        String user = scanner.nextLine().trim();

        System.out.println("Action DateTime From (yyyy-MM-dd HH:mm:ss): ");
        String dateTimeFrom = scanner.nextLine().trim();

        System.out.println("Action DateTime To (yyyy-MM-dd HH:mm:ss): ");
        String dateTimeTo = scanner.nextLine().trim();

        // Создание строки запроса
        StringBuilder queryBuilder = new StringBuilder();

        if (!actionType.isEmpty()) {
            queryBuilder.append("actionType:").append(actionType).append(";");
        }

        if (!user.isEmpty()) {
            queryBuilder.append("user:").append(user).append(";");
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
