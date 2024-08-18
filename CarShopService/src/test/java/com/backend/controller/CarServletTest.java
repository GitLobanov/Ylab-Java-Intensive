package com.backend.controller;

import com.backend.dto.CarDTO;
import com.backend.mapper.CarMapper;
import com.backend.model.Car;
import com.backend.service.CarService;
import com.backend.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServletTest {

    private CarServlet carServlet;
    private CarService carService;
    private ClientService clientService;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        carService = mock(CarService.class);
        clientService = mock(ClientService.class);
        objectMapper = new ObjectMapper();
        carServlet = new CarServlet();
    }

    @Test
    public void testDoGet() throws Exception {
        Car car = new Car();
        car.setId(1000);
        car.setModel("Test Car");

        when(carService.getAllCars()).thenReturn(List.of(car));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        carServlet.doGet(request, response);

        assertEquals(200, response.getStatus());
        String responseBody = new String(outputStream.toByteArray());
        CarDTO[] cars = objectMapper.readValue(responseBody, CarDTO[].class);
        assertEquals(1, cars.length);
        assertEquals("Test Car", cars[0].getModel());
    }

    @Test
    public void testDoPost() throws Exception {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(1000);
        carDTO.setModel("New Car");

        Car car = new Car();
        car.setId(1);
        car.setModel("New Car");

        when(carService.getCarById(1)).thenReturn(Optional.of(car));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        byte[] requestBody = objectMapper.writeValueAsBytes(carDTO);
        ServletInputStream inputStream = new MockServletInputStream(requestBody);
        when(request.getInputStream()).thenReturn(inputStream);

        carServlet.doPost(request, response);

        verify(carService, times(1)).addCar(car);
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
    }

    @Test
    public void testDoDelete() throws Exception {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(1000);

        Car car = new Car();
        car.setId(1000);

        when(carService.getCarById(1)).thenReturn(Optional.of(car));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        byte[] requestBody = objectMapper.writeValueAsBytes(carDTO);
        ServletInputStream inputStream = new MockServletInputStream(requestBody);
        when(request.getInputStream()).thenReturn(inputStream);

        carServlet.doDelete(request, response);

        verify(carService, times(1)).deleteCar(car);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    }

    @Test
    public void testDoPut() throws Exception {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(1000);
        carDTO.setModel("Updated Car");

        Car car = new Car();
        car.setId(1);
        car.setModel("Old Car");

        when(carService.getCarById(1)).thenReturn(Optional.of(car));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        byte[] requestBody = objectMapper.writeValueAsBytes(carDTO);
        ServletInputStream inputStream = new MockServletInputStream(requestBody);
        when(request.getInputStream()).thenReturn(inputStream);

        carServlet.doPut(request, response);

        verify(carService, times(1)).updateCar(car);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    }
}

