package com.backend.controller;

import com.backend.dto.EmployeeDTO;
import com.backend.mapper.EmployeeMapper;
import com.backend.model.User;
import com.backend.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final ObjectMapper objectMapper;
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.employeeMapper = EmployeeMapper.INSTANCE;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @GetMapping()
    private ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees =  employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/filter")
    private byte[] handleFilterEmployee(EmployeeDTO employeeDTO) throws IOException {
        User user = employeeMapper.toEntity(employeeDTO);
        return objectMapper.writeValueAsBytes(employeeService.getEmployeesBySearch(user));
    }

    @PostMapping
    public ResponseEntity<Void> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        User user = employeeMapper.toEntity(employeeDTO);
        employeeService.addEmployee(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String username) {
        Optional<User> optionalEmployee = employeeService.getByUsername(username);
        if (optionalEmployee.isPresent()) {
            employeeService.removeEmployee(username);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateEmployee(@PathVariable String username, @RequestBody EmployeeDTO employeeDTO) {
        Optional<User> userUpdate = employeeService.getByUsername(username);
        if (userUpdate.isPresent()) {
            employeeMapper.updateFromDto(employeeDTO, userUpdate.get());
            employeeService.updateEmployee(userUpdate.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
