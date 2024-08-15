package com.backend.service.impl;

import com.backend.model.ActionLog;
import com.backend.model.User;
import com.backend.repository.impl.UserRepository;
import com.backend.service.ActionLogService;
import com.backend.util.ConsoleColors;

public class EmployeeService {

    ActionLogService actionLogService;

    UserRepository userRepository;

    public EmployeeService () {
        userRepository = new UserRepository();

        actionLogService = new ActionLogService();

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

    public void viewAllEmployees() {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "View all employees");
        for (User user : userRepository.findAll()) {
            if (user.getRole() == User.Role.ADMIN || user.getRole() == User.Role.MANAGER) {
                System.out.println(ConsoleColors.PURPLE_BOLD + user + ConsoleColors.RESET);
            }
        }
    }

}
