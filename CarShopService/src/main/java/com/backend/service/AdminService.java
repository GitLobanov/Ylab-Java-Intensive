package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.User;
import com.backend.repository.ActionLogRepository;
import com.backend.repository.UserRepository;
import com.backend.service.parent.EmployeeAbstractService;
import com.backend.util.ConsoleColors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing admin-specific operations.
 * Extends the functionality of EmployeeAbstractService to include managing employees and action logs.
 */

public class AdminService extends EmployeeAbstractService {

    /**
     * Views all employees in the repository.
     * Logs the action and prints details of users with roles ADMIN or MANAGER.
     */

    public void viewAllEmployees() {
        log(ActionLog.ActionType.VIEW, "View all employees");
        for (User user : UserRepository.getInstance().findAll().values()) {
            if (user.getRole() == User.Role.ADMIN || user.getRole() == User.Role.MANAGER) {
                System.out.println(ConsoleColors.PURPLE_BOLD + user + ConsoleColors.RESET);
            }
        }
    }

    /**
     * Views all clients in the repository.
     * Logs the action and prints details of users with the role CLIENT.
     */

    public void viewAllClients () {
        log(ActionLog.ActionType.VIEW, "View all clients");

        for (User user : UserRepository.getInstance().findAll().values()) {
            if (user.getRole() == User.Role.CLIENT) {
                System.out.println(ConsoleColors.PURPLE_BOLD + user + ConsoleColors.RESET);
            }
        }
    }

    /**
     * Adds a new employee to the repository.
     * Logs the action and returns the result of the save operation.
     *
     * @param employee the employee to be added
     * @return true if the employee is successfully added, false otherwise
     */

    public boolean addEmployee(User employee) {
        log(ActionLog.ActionType.CREATE, "Created employee: " + employee.getUserName());
        return  UserRepository.getInstance().save(employee);
    }

    /**
     * Updates an existing employee's information in the repository.
     * Logs the action and returns the result of the update operation.
     *
     * @param updatedEmployee the updated employee information
     * @return true if the employee is successfully updated, false otherwise
     */

    public boolean updateEmployee(User updatedEmployee) {
        if (updatedEmployee==null) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "Employee not found." + ConsoleColors.RESET);
            return false;
        }
        log(ActionLog.ActionType.UPDATE, "Updated employee: " + updatedEmployee.getUserName());
        return UserRepository.getInstance().update(updatedEmployee);
    }

    /**
     * Removes an employee from the repository.
     * Logs the action and returns the result of the delete operation.
     *
     * @param userName the username of the employee to be removed
     * @return true if the employee is successfully removed, false otherwise
     */

    public boolean removeEmployee(String userName) {
        if (UserRepository.getInstance().findByUserName(userName)==null){
            System.out.println(ConsoleColors.YELLOW_BOLD + "Employee not found." + ConsoleColors.RESET);
            return false;
        }
        User employee = UserRepository.getInstance().findByUserName(userName);
        log(ActionLog.ActionType.DELETE, "Deleted employee: " + employee.getUserName());
        return UserRepository.getInstance().delete(employee);
    }

    /**
     * Searches the action logs based on a query string.
     * Filters the logs and displays the search results.
     *
     * @param query the search query string
     */

    public void searchActionLog(String query) {
        Map<UUID, ActionLog> logs = ActionLogRepository.getInstance().findAll();
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
                    logs = logs.values().stream()
                            .filter(log -> log.getActionType().toString().equalsIgnoreCase(value))
                            .collect(Collectors.toMap(ActionLog::getId, log -> log));
                    break;
                case "user":
                    logs = logs.values().stream()
                            .filter(log -> log.getUser().getUserName().equalsIgnoreCase(value))
                            .collect(Collectors.toMap(ActionLog::getId, log -> log));
                    break;
                case "actionDateTimeFrom<actionDateTime<actionDateTimeTo":
                    String[] dateTimeRange = value.split("<actionDateTime<");
                    LocalDateTime dateTimeFrom = LocalDateTime.parse(dateTimeRange[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDateTime dateTimeTo = LocalDateTime.parse(dateTimeRange[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    logs = logs.values().stream()
                            .filter(log -> log.getActionDateTime().isAfter(dateTimeFrom) && log.getActionDateTime().isBefore(dateTimeTo))
                            .collect(Collectors.toMap(ActionLog::getId, log -> log));
                    break;
                case "actionDateTimeFrom<actionDateTime":
                    LocalDateTime dateTimeOnlyFrom = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    logs = logs.values().stream()
                            .filter(log -> log.getActionDateTime().isAfter(dateTimeOnlyFrom))
                            .collect(Collectors.toMap(ActionLog::getId, log -> log));
                    break;
                case "actionDateTime<actionDateTimeTo":
                    LocalDateTime dateTimeOnlyTo = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    logs = logs.values().stream()
                            .filter(log -> log.getActionDateTime().isBefore(dateTimeOnlyTo))
                            .collect(Collectors.toMap(ActionLog::getId, log -> log));
                    break;
                default:
                    break;
            }
        }

        Iterator<Map.Entry<UUID, ActionLog>> iterator = logs.entrySet().iterator();
        displaySearchResultActionLog(iterator);


    }

    /**
     * Forms a query string for searching action logs based on specified criteria.
     *
     * @return the formatted query string
     */

    public String formingQuerySearchActionLogs() {
        Scanner scanner = new Scanner(System.in);

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
