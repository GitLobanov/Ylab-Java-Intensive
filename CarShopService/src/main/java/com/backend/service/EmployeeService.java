package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.User;
import com.backend.repository.impl.UserRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<User> getByUsername(String userName) {
        return userRepository.findByUserName(userName) == null ?
                Optional.empty() : Optional.of(userRepository.findByUserName(userName));
    }

    public List<User> getEmployeesBySearch(User user) {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "Search employees");
        return userRepository.search(user);
    }

    public List<User> getAllEmployees() {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "View all employees");
        return userRepository.findEmployee();
    }

}
