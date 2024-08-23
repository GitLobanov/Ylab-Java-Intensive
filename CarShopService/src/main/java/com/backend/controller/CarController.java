package com.backend.controller;

import com.backend.dto.CarDTO;
import com.backend.mapper.CarMapper;
import com.backend.model.Car;
import com.backend.service.CarService;
import com.backend.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
@Tag(name = "Car Controller", description = "Operations related to cars")
public class CarController {

    private final ObjectMapper objectMapper;
    private final CarMapper carMapper;
    private final CarService carService;
    private final ClientService clientService;

    @Autowired
    public CarController(CarService carService, ClientService clientService) {
        this.carService = carService;
        this.clientService = clientService;
        this.carMapper = CarMapper.INSTANCE;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Operation(summary = "Get all cars", description = "Fetch all cars from the database")
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        List<CarDTO> carDTOs = cars.stream()
                .map(carMapper::toDTO)
                .toList();
        return ResponseEntity.ok(carDTOs);
    }

    @PostMapping
    public ResponseEntity<Void> addCar(@RequestBody CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);
        carService.addCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable int id) {
        Optional<Car> carOptional = carService.getCarById(id);
        if (carOptional.isPresent()) {
            carService.deleteCar(carOptional.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable int id, @RequestBody CarDTO carDTO) {
        Optional<Car> carOptional = carService.getCarById(id);
        if (carOptional.isPresent()) {
            Car carUpdate = carOptional.get();
            carMapper.updateFromDto(carDTO, carUpdate);
            carService.updateCar(carUpdate);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}