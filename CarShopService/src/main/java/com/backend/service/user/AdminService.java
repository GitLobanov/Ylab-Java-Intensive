package com.backend.service.user;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.User;
import com.backend.repository.ActionLogRepository;
import com.backend.repository.CarRepository;
import com.backend.repository.UserRepository;
import com.backend.service.user.parent.EmployeeAbstractService;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class AdminService extends EmployeeAbstractService {


    // View all employees
    public void viewAllEmployees() {
        for (User user : UserRepository.getInstance().findAll().values()) {
            if (user.getRole() == User.Role.ADMIN || user.getRole() == User.Role.MANAGER) {
                System.out.println(ConsoleColors.PURPLE_BOLD + user + ConsoleColors.RESET);
            }
        }
    }

    public void viewAllClients () {
        for (User user : UserRepository.getInstance().findAll().values()) {
            if (user.getRole() == User.Role.CLIENT) {
                System.out.println(ConsoleColors.PURPLE_BOLD + user + ConsoleColors.RESET);
            }
        }
    }

    // Add employee
    public boolean addEmployee(User employee) {
        return  UserRepository.getInstance().save(employee);
    }

    // Update employee
    public boolean updateEmployee(String userName, User updatedEmployee) {
        if (UserRepository.getInstance().findByUserName(userName)==null) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "Employee not found." + ConsoleColors.RESET);
            return false;
        }
        User employee = UserRepository.getInstance().findByUserName(userName);
        return UserRepository.getInstance().update(updatedEmployee);
    }

    // Remove employee
    public boolean removeEmployee(String userName) {
        if (UserRepository.getInstance().findByUserName(userName)==null){
            System.out.println(ConsoleColors.YELLOW_BOLD + "Employee not found." + ConsoleColors.RESET);
            return false;
        }
        User employee = UserRepository.getInstance().findByUserName(userName);
        return UserRepository.getInstance().delete(employee);
    }

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

        // Удаление последнего символа ';' если строка не пустая
        String query = queryBuilder.length() > 0 ? queryBuilder.substring(0, queryBuilder.length() - 1) : "";

        return query;
    }

}
