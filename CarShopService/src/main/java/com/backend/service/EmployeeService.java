package com.backend.service;

import com.backend.dto.EmployeeDTO;
import com.backend.mapper.EmployeeMapper;
import com.backend.model.User;
import com.backend.repository.impl.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    UserRepository userRepository;
    EmployeeMapper employeeMapper;

    public EmployeeService () {
        userRepository = new UserRepository();
        employeeMapper = EmployeeMapper.INSTANCE;
    }

    public boolean addEmployee(User employee) {
        return userRepository.save(employee);
    }


    public boolean updateEmployee(User updatedEmployee) {
        if (updatedEmployee==null) {
            return false;
        }
        return userRepository.update(updatedEmployee);
    }

    public boolean removeEmployee(String userName) {
        if (userRepository.findByUserName(userName)==null){
            return false;
        }
        User employee = userRepository.findByUserName(userName);
        return userRepository.delete(employee);
    }

    public Optional<User> getByUsername(String userName) {
        return userRepository.findByUserName(userName) == null ?
                Optional.empty() : Optional.of(userRepository.findByUserName(userName));
    }

    public List<User> getEmployeesBySearch(User user) {
        return userRepository.search(user);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.getDTOs(userRepository.findEmployees());
    }

}
