package com.backend.controller;

import com.backend.dto.CarDTO;
import com.backend.dto.ClientDTO;
import com.backend.dto.EmployeeDTO;
import com.backend.mapper.ClientMapper;
import com.backend.mapper.EmployeeMapper;
import com.backend.model.User;
import com.backend.service.OrderService;
import com.backend.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ObjectMapper objectMapper;
    private final ClientMapper clientMapper;

    private final ClientService clientService;
    private final OrderService orderService;

    @Autowired
    public ClientController(ClientService clientService, OrderService orderService) {
        this.clientService = clientService;
        this.orderService = orderService;
        this.clientMapper = ClientMapper.INSTANCE;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<Void> addClient(@RequestBody ClientDTO clientDTO) {
        User user = clientMapper.toEntity(clientDTO);
        clientService.addClient(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteClient(@PathVariable String username) {
        Optional<User> optionalClient = clientService.getClientByUsername(username);
        if (optionalClient.isPresent()) {
            clientService.removeClient(username);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateClient(@PathVariable String username, @RequestBody ClientDTO clientDTO) {
        Optional<User> optionalClient = clientService.getClientByUsername(username);
        if (optionalClient.isPresent()) {
            User clientUpdate = optionalClient.get();
            clientMapper.updateFromDto(clientDTO, clientUpdate);
            clientService.updateClient(username, clientUpdate);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/cars")
    private ResponseEntity<List<CarDTO>> handleGetClientCars(ClientDTO clientDTO) {
        List<CarDTO> cars = clientService.getClientCars(clientDTO.getUsername());
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/manager")
    private ResponseEntity<List<ClientDTO>> handleGetManagerClients(EmployeeDTO employeeDTO) throws IOException {
        List<ClientDTO> clients = orderService.getClientsByManager(employeeDTO.getUsername());
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/filter")
    private ResponseEntity<List<ClientDTO>> handleFilterClients(ClientDTO clientDTO) throws IOException {
        List<ClientDTO> clients = clientService.getClientsBySearch(clientDTO);
        return ResponseEntity.ok(clients);
    }
}

