package com.backend.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.model.Car;
import com.backend.repository.impl.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = CarRepository.getInstance();
        carRepository.findAll().clear(); // Ensure the repository is empty before each test
    }

    @Test
    void testSave() {
        Car car = new Car("Toyota", "Camry", 2020, 20000.00, "New", "White", true);

        boolean result = carRepository.save(car);
        assertTrue(result, "Save operation should return true");

        Car fetchedCar = carRepository.findById(car.getId());
        assertNotNull(fetchedCar, "Saved car should be retrievable");
        assertEquals(car, fetchedCar, "Saved car should be equal to the fetched car");
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setId(UUID.randomUUID());
        car.setModel("Model S");
        car.setAvailability(true);

        carRepository.save(car);

        Car updatedCar = new Car();
        updatedCar.setId(car.getId());
        updatedCar.setModel("Model X");
        updatedCar.setAvailability(true);

        boolean result = carRepository.update(updatedCar);
        assertFalse(result, "Update operation should return false as not implemented");

        Car fetchedCar = carRepository.findById(car.getId());
        assertNotNull(fetchedCar, "Car should still be retrievable");
        assertEquals("Model S", fetchedCar.getModel(), "Car model should remain unchanged");
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setId(UUID.randomUUID());
        car.setModel("Model S");
        car.setAvailability(true);

        carRepository.save(car);

        boolean result = carRepository.delete(car);
        assertTrue(result, "Delete operation should return true");

        Car fetchedCar = carRepository.findById(car.getId());
        assertNull(fetchedCar, "Deleted car should not be retrievable");
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setId(UUID.randomUUID());
        car1.setModel("Model S");
        car1.setAvailability(true);

        Car car2 = new Car();
        car2.setId(UUID.randomUUID());
        car2.setModel("Model X");
        car2.setAvailability(false);

        carRepository.save(car1);
        carRepository.save(car2);

        Map<UUID, Car> allCars = carRepository.findAll();
        assertEquals(2, allCars.size(), "Should return all cars in repository");
        assertTrue(allCars.containsKey(car1.getId()), "All cars should include car1");
        assertTrue(allCars.containsKey(car2.getId()), "All cars should include car2");
    }

    @Test
    void testFindAllAvailableCars() {
        Car availableCar = new Car();
        availableCar.setId(UUID.randomUUID());
        availableCar.setModel("Model S");
        availableCar.setAvailability(true);

        Car unavailableCar = new Car();
        unavailableCar.setId(UUID.randomUUID());
        unavailableCar.setModel("Model X");
        unavailableCar.setAvailability(false);

        carRepository.save(availableCar);
        carRepository.save(unavailableCar);

        Map<UUID, Car> availableCars = carRepository.findAllAvailableCars();
        assertEquals(1, availableCars.size(), "Should only return available cars");
        assertTrue(availableCars.containsKey(availableCar.getId()), "Available cars should include availableCar");
        assertFalse(availableCars.containsKey(unavailableCar.getId()), "Available cars should not include unavailableCar");
    }
}

