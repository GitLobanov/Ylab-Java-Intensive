package com.backend.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

class OrderRepositoryTest {

    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = OrderRepository.getInstance();
        orderRepository.findAll().clear(); // Ensure the repository is empty before each test
    }

    @Test
    void testSave() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setType(Order.TypeOrder.BUYING);
        order.setClient(new User());
        order.setManager(new User());

        boolean result = orderRepository.save(order);
        assertTrue(result, "Save operation should return true");

        Order fetchedOrder = orderRepository.findById(order.getId());
        assertNotNull(fetchedOrder, "Saved order should be retrievable");
        assertEquals(order, fetchedOrder, "Saved order should be equal to the fetched order");
    }

    @Test
    void testUpdate() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setType(Order.TypeOrder.BUYING);
        order.setClient(new User());
        order.setManager(new User());

        orderRepository.save(order);

        Order updatedOrder = new Order();
        updatedOrder.setId(order.getId());
        updatedOrder.setType(Order.TypeOrder.SERVICE);
        updatedOrder.setClient(new User());
        updatedOrder.setManager(new User());

        boolean result = orderRepository.update(updatedOrder);
        assertFalse(result, "Update operation should return false as not implemented");

        Order fetchedOrder = orderRepository.findById(order.getId());
        assertNotNull(fetchedOrder, "Order should still be retrievable");
        assertEquals(Order.TypeOrder.BUYING, fetchedOrder.getType(), "Order type should remain unchanged");
    }

    @Test
    void testDelete() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setType(Order.TypeOrder.BUYING);
        order.setClient(new User());
        order.setManager(new User());

        orderRepository.save(order);

        boolean result = orderRepository.delete(order);
        assertFalse(result, "Delete operation should return false as not implemented");

        Order fetchedOrder = orderRepository.findById(order.getId());
        assertNotNull(fetchedOrder, "Order should still be retrievable");
    }

    @Test
    void testFindAll() {
        Order order1 = new Order();
        order1.setId(UUID.randomUUID());
        order1.setType(Order.TypeOrder.BUYING);
        order1.setClient(new User());
        order1.setManager(new User());

        Order order2 = new Order();
        order2.setId(UUID.randomUUID());
        order2.setType(Order.TypeOrder.SERVICE);
        order2.setClient(new User());
        order2.setManager(new User());

        orderRepository.save(order1);
        orderRepository.save(order2);

        Map<UUID, Order> allOrders = orderRepository.findAll();
        assertEquals(2, allOrders.size(), "Should return all orders in repository");
        assertTrue(allOrders.containsKey(order1.getId()), "All orders should include order1");
        assertTrue(allOrders.containsKey(order2.getId()), "All orders should include order2");
    }

    @Test
    void testFindByType() {
        Order buyingOrder = new Order();
        buyingOrder.setId(UUID.randomUUID());
        buyingOrder.setType(Order.TypeOrder.BUYING);
        buyingOrder.setClient(new User());
        buyingOrder.setManager(new User());

        Order serviceOrder = new Order();
        serviceOrder.setId(UUID.randomUUID());
        serviceOrder.setType(Order.TypeOrder.SERVICE);
        serviceOrder.setClient(new User());
        serviceOrder.setManager(new User());

        orderRepository.save(buyingOrder);
        orderRepository.save(serviceOrder);

        Map<UUID, Order> buyingOrders = orderRepository.findByType(Order.TypeOrder.BUYING);
        assertEquals(1, buyingOrders.size(), "Should return only buying orders");
        assertTrue(buyingOrders.containsKey(buyingOrder.getId()), "Buying orders should include buyingOrder");

        Map<UUID, Order> serviceOrders = orderRepository.findByType(Order.TypeOrder.SERVICE);
        assertEquals(1, serviceOrders.size(), "Should return only service orders");
        assertTrue(serviceOrders.containsKey(serviceOrder.getId()), "Service orders should include serviceOrder");
    }

    @Test
    void testFindByClient() {
        User client = new User();
        client.setId(UUID.randomUUID());

        Order orderForClient = new Order();
        orderForClient.setId(UUID.randomUUID());
        orderForClient.setType(Order.TypeOrder.BUYING);
        orderForClient.setClient(client);
        orderForClient.setManager(new User());

        Order orderForOtherClient = new Order();
        orderForOtherClient.setId(UUID.randomUUID());
        orderForOtherClient.setType(Order.TypeOrder.SERVICE);
        orderForOtherClient.setClient(new User());
        orderForOtherClient.setManager(new User());

        orderRepository.save(orderForClient);
        orderRepository.save(orderForOtherClient);

        Map<UUID, Order> ordersForClient = orderRepository.findByClient(client);
        assertEquals(1, ordersForClient.size(), "Should return only orders for the specified client");
        assertTrue(ordersForClient.containsKey(orderForClient.getId()), "Orders for client should include orderForClient");
    }

    @Test
    void testFindByManager() {
        User manager = new User();
        manager.setId(UUID.randomUUID());

        Order orderForManager = new Order();
        orderForManager.setId(UUID.randomUUID());
        orderForManager.setType(Order.TypeOrder.BUYING);
        orderForManager.setClient(new User());
        orderForManager.setManager(manager);

        Order orderForOtherManager = new Order();
        orderForOtherManager.setId(UUID.randomUUID());
        orderForOtherManager.setType(Order.TypeOrder.SERVICE);
        orderForOtherManager.setClient(new User());
        orderForOtherManager.setManager(new User());

        orderRepository.save(orderForManager);
        orderRepository.save(orderForOtherManager);

        Map<UUID, Order> ordersForManager = orderRepository.findByManager(manager);
        assertEquals(1, ordersForManager.size(), "Should return only orders for the specified manager");
        assertTrue(ordersForManager.containsKey(orderForManager.getId()), "Orders for manager should include orderForManager");
    }
}
