package com.backend.controller;


import com.backend.dto.CarDTO;
import com.backend.mapper.CarMapper;
import com.backend.model.Car;
import com.backend.service.CarService;
import com.backend.service.impl.ClientService;
import com.backend.util.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "CarServlet", urlPatterns = "/api/cars")
public class CarServlet extends HttpServlet {

    private final ObjectMapper objectMapper;
    private CarMapper carMapper = CarMapper.INSTANCE;
    private CarService carService;
    private ClientService clientService;

    public CarServlet() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        carService = new CarService();
        clientService = new ClientService();
        Session session = Session.getInstance();
        session.setUser(clientService.getClientByUsername("man").get());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] bytes = objectMapper.writeValueAsBytes(carService.getAllCars());
        resp.getOutputStream().write(bytes);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Car car = carMapper.toEntity(getDTO(req));

        carService.addCar(car);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Car> carOptional = carService.getCarById(getDTO(req).getId());

        if (carOptional.isPresent()) {
            carService.deleteCar(carOptional.get());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Car not found");
            return;
        }

        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarDTO carDTO = getDTO(req);
        Optional<Car> carOptional = carService.getCarById(carDTO.getId());

        if (carOptional.isPresent()) {
            Car carUpdate = carOptional.get();
            carMapper.updateFromDto(carDTO, carUpdate);
            carService.updateCar(carOptional.get());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Car not found");
        }

        doGet(req, resp);
    }

    private CarDTO getDTO(HttpServletRequest req) throws IOException {
        CarDTO carDTO = objectMapper.readValue(req.getInputStream(), CarDTO.class);
        return carDTO;
    }
}
