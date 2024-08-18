package com.backend.service.impl;

import com.backend.model.ActionLog;
import com.backend.model.User;
import com.backend.repository.impl.UserRepository;
import com.backend.service.ActionLogService;

import java.util.List;

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
            return false;
        }
        actionLogService.logAction(ActionLog.ActionType.UPDATE, "Updated employee: " + updatedEmployee.getUsername());
        return userRepository.update(updatedEmployee);
    }

    public boolean removeEmployee(String userName) {
        if (userRepository.findByUserName(userName)==null){
            return false;
        }
        User employee = userRepository.findByUserName(userName);
        actionLogService.logAction(ActionLog.ActionType.DELETE, "Deleted employee: " + employee.getUsername());
        return userRepository.delete(employee);
    }

    public User getByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<User> getAllEmployees() {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "View all employees");
        return userRepository.findUsersByRole(User.Role.MANAGER);
    }

}
