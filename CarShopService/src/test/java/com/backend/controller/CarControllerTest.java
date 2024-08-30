package com.backend.controller;

import com.backend.dto.CarDTO;
import com.backend.service.CarService;
import com.backend.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private ClientService clientService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Test getting all cars")
    public void testGetAllCars() throws Exception {
        CarDTO carDTO = new CarDTO();
        List<CarDTO> carList = Collections.singletonList(carDTO);

        when(carService.getAllCars()).thenReturn(carList);

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    @DisplayName("Test adding a car")
    public void testAddCar() throws Exception {
        CarDTO carDTO = new CarDTO();

        mockMvc.perform(post("/api/cars")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carDTO)))
                .andExpect(status().isCreated());

        verify(carService, times(1)).addCar(any(CarDTO.class));
    }

    @Test
    @DisplayName("Test deleting a car")
    public void testDeleteCar() throws Exception {
        int carId = 1;
        when(carService.deleteCar(carId)).thenReturn(true);
        mockMvc.perform(delete("/api/cars/{id}", carId))
                .andExpect(status().isOk());

        verify(carService, times(1)).deleteCar(carId);
    }

    @Test
    @DisplayName("Test deleting a car not found")
    public void testDeleteCarNotFound() throws Exception {
        int carId = 1;
        when(carService.deleteCar(carId)).thenReturn(false);

        mockMvc.perform(delete("/api/cars/{id}", carId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test updating a car")
    public void testUpdateCar() throws Exception {
        CarDTO carDTO = new CarDTO();

        when(carService.updateCar(carDTO)).thenReturn(true);

        mockMvc.perform(put("/api/cars/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carDTO)))
                .andExpect(status().isOk());

        verify(carService, times(1)).updateCar(any(CarDTO.class));
    }

    @Test
    @DisplayName("Test updating a car not found")
    public void testUpdateCarNotFound() throws Exception {
        CarDTO carDTO = new CarDTO();

        when(carService.updateCar(carDTO)).thenReturn(false);

        mockMvc.perform(put("/api/cars/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carDTO)))
                .andExpect(status().isNotFound());
    }
}
