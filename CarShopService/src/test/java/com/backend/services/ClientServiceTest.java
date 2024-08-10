package com.backend.services;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.ActionLogRepository;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    private ClientService clientService;
    private ActionLogRepository actionLogRepository;
    private OrderRepository orderRepository;
    private CarRepository carRepository;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        clientService = new ClientService();

        actionLogRepository = mock(ActionLogRepository.class);
        orderRepository = mock(OrderRepository.class);
        carRepository = mock(CarRepository.class);

        // Use mock data
        Map<UUID, ActionLog> actionLogs = new HashMap<>();
        ActionLog log = new ActionLog();
        log.setActionType(ActionLog.ActionType.VIEW);
        log.setUser(new User());
        log.getUser().setUserName("client");
        log.setActionDateTime(LocalDateTime.now());
        log.setMessage("test action log");
        actionLogs.put(UUID.randomUUID(), log);

        when(actionLogRepository.findAll()).thenReturn(actionLogs);
        ActionLogRepository.setInstance(actionLogRepository);  // If static method available for setting mock

        User client = new User();
        client.setUserName("client");

        Map<UUID, Order> orders = new HashMap<>();
        Order order = new Order();
        order.setCar(new Car());
        order.setType(Order.TypeOrder.BUYING);
        orders.put(UUID.randomUUID(), order);

        when(orderRepository.findByClient(client)).thenReturn(orders);
        OrderRepository.setInstance(orderRepository);  // If static method available for setting mock
    }

    @Test
    void testViewMyActionLog() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        User client = new User();
        client.setUserName("client");

        clientService.viewMyActionLog();

        String output = outputStream.toString();
        assertTrue(output.contains("test action log"), "Output should contain action log message");

        System.setOut(originalOut);
    }

    @Test
    void testViewMyCars() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        User client = new User();
        client.setUserName("client");

        clientService.viewMyCars(client);

        String output = outputStream.toString();
        assertTrue(output.contains("Car"), "Output should contain car information");

        System.setOut(originalOut);
    }


}
