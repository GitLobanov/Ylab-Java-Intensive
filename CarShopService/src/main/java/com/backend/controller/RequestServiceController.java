package com.backend.controller;

import com.backend.dto.ClientDTO;
import com.backend.dto.OrderDTO;
import com.backend.mapper.OrderMapper;
import com.backend.model.Order;
import com.backend.service.OrderService;
import com.backend.service.ClientService;
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
@RequestMapping("/api/requests")
public class RequestServiceController {

    private final ObjectMapper objectMapper;
    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final ClientService clientService;

    @Autowired
    public RequestServiceController(OrderService orderService, ClientService clientService) {
        this.orderService = orderService;
        this.orderMapper = OrderMapper.INSTANCE;
        this.clientService = clientService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllRequests() throws IOException {
        List<OrderDTO> orders = orderService.getAllBuyingOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/client")
    public ResponseEntity<List<OrderDTO>> getClientRequests(@RequestBody ClientDTO clientDTO) throws IOException {
        List<OrderDTO> orders = orderService.getClientRequests(clientDTO.getUsername());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/filter")
    public byte[] filterRequests(@RequestBody OrderDTO orderDTO) throws IOException {
        return objectMapper.writeValueAsBytes(orderService.getOrdersBySearch(orderDTO));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) throws IOException {
        Order order = orderMapper.toEntity(orderDTO);
        if (orderService.addOrder(order)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create order");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteOrder(@RequestBody OrderDTO orderDTO) throws IOException {
        Optional<Order> optionalOrder = orderService.getOrderById(orderDTO.getId());

        if (optionalOrder.isPresent()) {
            orderService.deleteOrder(optionalOrder.get());
            return ResponseEntity.ok("Order deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO) throws IOException {
        Optional<Order> optionalOrder = orderService.getOrderById(orderDTO.getId());

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
