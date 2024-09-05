package com.backend.controller;

import com.backend.dto.ClientDTO;
import com.backend.dto.OrderDTO;
import com.backend.loggerstarter.annotation.EnableAudit;
import com.backend.mapper.OrderMapper;
import com.backend.model.Order;
import com.backend.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @EnableAudit(actionType = "Get", description = "Get all orders")
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders () {
        List<OrderDTO> orders = orderService.getAllBuyingOrders();
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @EnableAudit(actionType = "Get", description = "Get orders by client")
    @GetMapping("/client")
    public ResponseEntity<List<OrderDTO>> getClientOrders (@RequestBody ClientDTO clientDTO) throws IOException {
        List<OrderDTO> orders = orderService.getClientOrders(clientDTO.getUsername());
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @EnableAudit(actionType = "Get", description = "Filter orders")
    @GetMapping("/filter")
    public ResponseEntity<List<OrderDTO>> filterOrders(@RequestBody OrderDTO orderDTO) throws IOException {
        List<OrderDTO> orders = orderService.getOrdersBySearch(orderDTO);
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @EnableAudit(actionType = "Add", description = "Add order")
    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        if (orderService.addOrder(order)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create order");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @EnableAudit(actionType = "Delete", description = "Delete order")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        Optional<Order> optionalOrder = orderService.getOrderById(id);

        if (optionalOrder.isPresent()) {
            orderService.deleteOrder(optionalOrder.get());
            return ResponseEntity.ok("Order deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @EnableAudit(actionType = "Update", description = "Update order")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable int id, @RequestBody OrderDTO orderDTO) {
        Optional<Order> optionalOrder = orderService.getOrderById(id);

        if (optionalOrder.isPresent()) {
            Order orderUpdate = optionalOrder.get();
            orderMapper.updateFromDto(orderDTO, orderUpdate);
            orderService.updateOrder(orderUpdate);
            return ResponseEntity.ok("Order updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }
}
