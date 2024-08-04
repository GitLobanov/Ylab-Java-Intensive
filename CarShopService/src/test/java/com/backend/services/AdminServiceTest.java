package com.backend.services;

import com.backend.repository.ActionLogRepository;
import com.backend.repository.UserRepository;
import com.backend.service.user.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.backend.model.*;

public class AdminServiceTest {


    private AdminService adminService;
    private UserRepository userRepository;
    private ActionLogRepository actionLogRepository;

    @BeforeEach
    void setUp() {
        // Initialize the AdminService instance
        adminService = new AdminService();

        // Initialize the repositories with mocks
        userRepository = mock(UserRepository.class);
        actionLogRepository = mock(ActionLogRepository.class);

        // Use mock data
        Map<UUID, User> users = new HashMap<>();
        User adminUser = new User();
        adminUser.setRole(User.Role.ADMIN);
        adminUser.setUserName("admin");
        users.put(UUID.randomUUID(), adminUser);

        User clientUser = new User();
        clientUser.setRole(User.Role.CLIENT);
        clientUser.setUserName("client");
        users.put(UUID.randomUUID(), clientUser);

        when(userRepository.findAll()).thenReturn(users);
        UserRepository.setInstance(userRepository);  // Use static method if available for setting mock
    }

    @Test
    void testViewAllEmployees() {
        // Capture output
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Test viewAllEmployees
        adminService.viewAllEmployees();

        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("admin"), "Output should contain admin");
        assertFalse(output.contains("client"), "Output should not contain client");

        // Restore original output
        System.setOut(originalOut);
    }

    @Test
    void testViewAllClients() {
        // Capture output
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Test viewAllClients
        adminService.viewAllClients();

        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("client"), "Output should contain client");
        assertFalse(output.contains("admin"), "Output should not contain admin");

        // Restore original output
        System.setOut(originalOut);
    }

    @Test
    void testAddEmployee() {
        User newEmployee = new User();
        newEmployee.setUserName("newEmployee");

        when(userRepository.save(newEmployee)).thenReturn(true);

        boolean result = adminService.addEmployee(newEmployee);
        assertTrue(result, "Employee should be added successfully");
    }

    @Test
    void testUpdateEmployee() {
        User updatedEmployee = new User();
        updatedEmployee.setUserName("updatedEmployee");

        when(userRepository.update(updatedEmployee)).thenReturn(true);

        boolean result = adminService.updateEmployee(updatedEmployee);
        assertTrue(result, "Employee should be updated successfully");
    }

    @Test
    void testRemoveEmployee() {
        User employeeToRemove = new User();
        employeeToRemove.setUserName("employeeToRemove");

        when(userRepository.findByUserName("employeeToRemove")).thenReturn(employeeToRemove);
        when(userRepository.delete(employeeToRemove)).thenReturn(true);

        boolean result = adminService.removeEmployee("employeeToRemove");
        assertTrue(result, "Employee should be removed successfully");
    }

    @Test
    void testSearchActionLog() {
        Map<UUID, ActionLog> actionLogs = new HashMap<>();
        ActionLog log = new ActionLog();
        log.setActionType(ActionLog.ActionType.VIEW);
        log.setUser(new User());
        log.getUser().setUserName("user");
        log.setActionDateTime(LocalDateTime.now());
        log.setMessage("test message");

        actionLogs.put(UUID.randomUUID(), log);

        when(actionLogRepository.findAll()).thenReturn(actionLogs);
        ActionLogRepository.setInstance(actionLogRepository);  // Use static method if available for setting mock

        String query = "actionType:VIEW;";
        adminService.searchActionLog(query);

        // Verify output
        // Implement verification based on how `displaySearchResultActionLog` is used
    }

}