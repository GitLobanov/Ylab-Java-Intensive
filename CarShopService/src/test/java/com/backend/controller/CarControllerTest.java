package com.backend.controller;

import com.backend.dto.CarDTO;
import com.backend.service.CarService;
import com.backend.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    @Mock
    private ClientService clientService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
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
    public void testAddCar() throws Exception {
        CarDTO carDTO = new CarDTO();

        mockMvc.perform(post("/api/cars")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carDTO)))
                .andExpect(status().isCreated());

        verify(carService, times(1)).addCar(any(CarDTO.class));
    }

    @Test
    public void testDeleteCar() throws Exception {
        int carId = 1;
        when(carService.deleteCar(carId)).thenReturn(true);
        mockMvc.perform(delete("/api/cars/{id}", carId))
                .andExpect(status().isOk());

        verify(carService, times(1)).deleteCar(carId);
    }

    @Test
    public void testDeleteCarNotFound() throws Exception {
        int carId = 1;
        when(carService.deleteCar(carId)).thenReturn(false);

        mockMvc.perform(delete("/api/cars/{id}", carId))
                .andExpect(status().isNotFound());
    }

    @Test
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
    public void testUpdateCarNotFound() throws Exception {
        CarDTO carDTO = new CarDTO();

        when(carService.updateCar(carDTO)).thenReturn(false);

        mockMvc.perform(put("/api/cars/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carDTO)))
                .andExpect(status().isNotFound());
    }
}
