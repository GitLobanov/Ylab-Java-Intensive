package com.backend.services;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ManagerServiceTest {

    private ManagerService managerService;
    private OrderRepository orderRepository;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        managerService = new ManagerService();

        orderRepository = mock(OrderRepository.class);
        OrderRepository.setInstance(orderRepository);
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testViewMyClients() {
        User manager = new User("john_man", "123", User.Role.MANAGER, "John Doe", "john.doe@example.com", "123-456-7890");

        User client1 = new User("john_niko", "123", User.Role.CLIENT, "John Doe", "john.doe@mail.com", "123-456-7890");
        User client2 = new User("jo_bo", "123", User.Role.CLIENT, "John Jabro", "john.doe@yndex.com", "123-456-7890");

        Car car = new Car("Toyota", "Camry", 2020, 20000.00, "New", "White", true);

        Order order1 = new Order(car, client1, Order.TypeOrder.BUYING, "Something to note 1");
        Order order2 = new Order(car, client2, Order.TypeOrder.BUYING, "Something to note 2");

        Map<UUID, Order> orders = new HashMap<>();
        orders.put(order1.getId(), order1);
        orders.put(order2.getId(), order2);

        when(OrderRepository.getInstance().findByManager(manager)).thenReturn(orders);

        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        managerService.viewMyClients(manager);

        String output = outputStream.toString();
        assertTrue(output.contains("john_niko"), "Output should contain client1 information");
        assertTrue(output.contains("jo_bo"), "Output should contain client2 information");

        System.setOut(System.out); // Restore original output
    }

    @Test
    void testViewMyTakenOrders() {
        User manager = new User("john_man", "123", User.Role.MANAGER, "John Doe", "john.doe@example.com", "123-456-7890");

        User client1 = new User("john_niko", "123", User.Role.CLIENT, "John Doe", "john.doe@mail.com", "123-456-7890");
        User client2 = new User("jo_bo", "123", User.Role.CLIENT, "John Jabro", "john.doe@yndex.com", "123-456-7890");

        Car car1 = new Car("Toyota", "Camry", 2020, 20000.00, "New", "White", true);
        Car car2 = new Car("Honda", "Civic", 2019, 18000.00, "Used", "Black", true);

        Order buyingOrder = new Order(car1, client1, Order.TypeOrder.BUYING, "Something to note 1");
        Order serviceOrder = new Order(car2, client2, Order.TypeOrder.SERVICE, "Something to note 2");

        Map<UUID, Order> orders = new HashMap<>();
        orders.put(UUID.randomUUID(), buyingOrder);
        orders.put(UUID.randomUUID(), serviceOrder);

        when(OrderRepository.getInstance().findByManager(manager)).thenReturn(orders);

        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        managerService.viewMyTakenOrders(manager);

        String output = outputStream.toString();
        assertTrue(output.contains("Camry"), "Output should contain car model information for buying orders");

        System.setOut(System.out); // Restore original output
    }

    @Test
    void testViewMyTakenRequests() {
        User manager = new User("john_man", "123", User.Role.MANAGER, "John Doe", "john.doe@example.com", "123-456-7890");

        User client1 = new User("john_niko", "123", User.Role.CLIENT, "John Doe", "john.doe@mail.com", "123-456-7890");
        User client2 = new User("jo_bo", "123", User.Role.CLIENT, "John Jabro", "john.doe@yndex.com", "123-456-7890");

        Car car1 = new Car("Toyota", "Camry", 2020, 20000.00, "New", "White", true);
        Car car2 = new Car("Honda", "Civic", 2019, 18000.00, "Used", "Black", true);


        Order buyingOrder = new Order(car1, client1, Order.TypeOrder.BUYING, "Something to note 1");
        Order serviceOrder = new Order(car2, client2, Order.TypeOrder.SERVICE, "Something to note 2");

        Map<UUID, Order> orders = new HashMap<>();
        orders.put(UUID.randomUUID(), serviceOrder);
        orders.put(UUID.randomUUID(), buyingOrder);

        when(OrderRepository.getInstance().findByManager(manager)).thenReturn(orders);

        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        managerService.viewMyTakenRequests(manager);

        String output = outputStream.toString();
        assertTrue(output.contains("Civic"), "Output should contain car model information for service requests");

        System.setOut(System.out); // Restore original output
    }

}