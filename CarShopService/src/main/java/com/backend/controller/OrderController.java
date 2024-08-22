package com.backend.controller;

import com.backend.dto.ClientDTO;
import com.backend.dto.OrderDTO;
import com.backend.mapper.OrderMapper;
import com.backend.model.Order;
import com.backend.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping
    public byte[] getAllOrders() throws IOException {
        return objectMapper.writeValueAsBytes(orderService.getAllBuyingOrders());
    }

    @GetMapping("/client")
    public byte[] getClientOrders(@RequestBody ClientDTO clientDTO) throws IOException {
        return objectMapper.writeValueAsBytes(orderService.getClientOrders(clientDTO.getUsername()));
    }

    @GetMapping("/filter")
    public byte[] filterOrders(@RequestBody OrderDTO orderDTO) throws IOException {
        Order order = orderMapper.toEntity(orderDTO);
        return objectMapper.writeValueAsBytes(orderService.getOrdersBySearch(order));
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        if (orderService.addOrder(order)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create order");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable int id) {
        Optional<Order> optionalOrder = orderService.getOrderById(id);

        if (optionalOrder.isPresent()) {
            orderService.deleteOrder(optionalOrder.get());
            return ResponseEntity.ok("Order deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable int id, @RequestBody OrderDTO orderDTO) {
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
