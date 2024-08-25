package com.backend.controller;

import com.backend.dto.CarDTO;
import com.backend.dto.ClientDTO;
import com.backend.dto.EmployeeDTO;
import com.backend.mapper.ClientMapper;
import com.backend.model.User;
import com.backend.service.ClientService;
import com.backend.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @Mock
    private OrderService orderService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllClients() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        List<ClientDTO> clients = Collections.singletonList(clientDTO);

        when(clientService.getAllClients()).thenReturn(clients);

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    public void testAddClient() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setUsername("user228");
        clientDTO.setId(200);
        User user = new User();
        user.setUsername("user228");
        user.setId(200);

        when(clientService.addClient(any(User.class))).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/clients")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated());

        verify(clientService, times(1)).addClient(any(User.class));
    }

    @Test
    public void testDeleteClient() throws Exception {
        String username = "testUser";
        when(clientService.getClientByUsername(username)).thenReturn(Optional.of(new User()));

        mockMvc.perform(delete("/api/clients/{username}", username))
                .andExpect(status().isOk());

        verify(clientService, times(1)).removeClient(username);
    }

    @Test
    public void testDeleteClientNotFound() throws Exception {
        String username = "testUser";
        when(clientService.getClientByUsername(username)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/clients/{username}", username))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateClient() throws Exception {
        String username = "testUser";
        ClientDTO clientDTO = new ClientDTO();
        User user = new User();

        when(clientService.getClientByUsername(username)).thenReturn(Optional.of(user));

        mockMvc.perform(put("/api/clients/{username}", username)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk());

        verify(clientService, times(1)).updateClient(eq(username), any(User.class));
    }

    @Test
    public void testUpdateClientNotFound() throws Exception {
        String username = "testUser";
        ClientDTO clientDTO = new ClientDTO();

        when(clientService.getClientByUsername(username)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/clients/{username}", username)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testHandleGetClientCars() throws Exception {
        String username = "testUser";
        CarDTO carDTO = new CarDTO();
        List<CarDTO> cars = Collections.singletonList(carDTO);

        when(clientService.getClientCars(username)).thenReturn(cars);

        mockMvc.perform(get("/api/clients/cars")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    public void testHandleGetManagerClients() throws Exception {
        String managerUsername = "managerUser";
        ClientDTO clientDTO = new ClientDTO();
        List<ClientDTO> clients = Collections.singletonList(clientDTO);

        when(orderService.getClientsByManager(managerUsername)).thenReturn(clients);

        mockMvc.perform(get("/api/clients/manager")
                        .param("username", managerUsername))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    public void testHandleFilterClients() throws Exception {
        ClientDTO filterDTO = new ClientDTO();
        List<ClientDTO> clients = Collections.singletonList(filterDTO);

        when(clientService.getClientsBySearch(any(ClientDTO.class))).thenReturn(clients);

        mockMvc.perform(get("/api/clients/filter")
                        .param("username", "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }
}
