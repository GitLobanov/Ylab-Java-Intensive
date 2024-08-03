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

import java.util.*;

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
        if (employee.getRole() == User.Role.ADMIN || employee.getRole() == User.Role.MANAGER) {
            updatedEmployee.setRole(employee.getRole()); // Ensure role remains the same
            UserRepository.getInstance().update(updatedEmployee);
            return true;
        } else {
            System.out.println(ConsoleColors.YELLOW_BOLD + "User role must be ADMIN or MANAGER." + ConsoleColors.RESET);
            return false;
        }
    }

    // Remove employee
    public boolean removeEmployee(String userName) {
        if (UserRepository.getInstance().findByUserName(userName)==null){
            System.out.println(ConsoleColors.YELLOW_BOLD + "Employee not found." + ConsoleColors.RESET);
            return false;
        }
        User employee = UserRepository.getInstance().findByUserName(userName);
        if (employee.getRole() == User.Role.ADMIN || employee.getRole() == User.Role.MANAGER) {
            UserRepository.getInstance().delete(employee);
            return true;
        } else {
            System.out.println(ConsoleColors.YELLOW_BOLD + "User role must be ADMIN or MANAGER." + ConsoleColors.RESET);
            return false;
        }
    }

    @Override
    public Map<UUID, ActionLog> viewMyActionLog() {
        return ActionLogRepository.getInstance().findAll();
    }

}
