package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.OrderRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CarService {

    CarRepository carRepository;
    OrderRepository orderRepository;
    ActionLogService actionLogService;

    public CarService () {
        carRepository = new CarRepository();
        orderRepository = new OrderRepository();
        actionLogService = new ActionLogService();
    }

    public boolean addCar(Car car) {
        actionLogService.logAction(ActionLog.ActionType.CREATE, "Created car");
        return carRepository.save(car);
    }


    public boolean updateCar(Car car) {
        actionLogService.logAction(ActionLog.ActionType.CREATE, "Updated car");
        return carRepository.update(car);
    }

    public boolean deleteCar(Car car) {
        actionLogService.logAction(ActionLog.ActionType.DELETE, "Deleted car");
        return carRepository.delete(car);
    }

    public Car findCarById(int id) {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "Searching for car");
        Car car = carRepository.findById(id);
        return car;
    }

    public List<Car> getAvailableCars() {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "Get available cars");
        List<Car> list = carRepository.findAllAvailableCars();
        return list;
    }

    public Car getCarById(int id) {
        return carRepository.findById(id);
    }

    public List<Car> getAllCars() {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "View all cars");
        return carRepository.findAll();
    }

    public List<Car> getCarsBySearch(String query) {

        actionLogService.logAction(ActionLog.ActionType.VIEW, "Search cars");

        List<Car> cars = carRepository.findAllAvailableCars();
        String[] params = query.split(";");

        for (String param : params) {
            String[] keyValue = param.split(":");

            if (keyValue.length < 2) {
                continue;
            }

            String key = keyValue[0];
            String value = keyValue[1];



            switch (key) {
                case "brand":
                    cars = cars.stream()
                            .filter(car -> car.getBrand().equalsIgnoreCase(value))
                            .collect(Collectors.toList());
                    break;
                case "color":
                    cars = cars.stream()
                            .filter(car -> car.getColor().equalsIgnoreCase(value))
                            .collect(Collectors.toList());
                    break;
                case "priceFrom<price<priceTo":
                    String[] priceRange = value.split("<price<");
                    double priceRangeFrom = Double.parseDouble(priceRange[0]);
                    double priceRangeTo = Double.parseDouble(priceRange[1]);
                    cars = cars.stream()
                            .filter(car -> car.getPrice() >= priceRangeFrom && car.getPrice() <= priceRangeTo)
                            .collect(Collectors.toList());
                    break;
                case "priceFrom<price":
                    String[] priceOnlyFrom = value.split("<price");
                    double priceFrom = Double.parseDouble(priceOnlyFrom[0]);
                    cars = cars.stream()
                            .filter(car -> car.getPrice() >= priceFrom)
                            .collect(Collectors.toList());
                    break;
                case "price<priceTo":
                    String[] priceOnlyTo = value.split("price<");
                    double priceTo = Double.parseDouble(priceOnlyTo[0]);
                    cars = cars.stream()
                            .filter(car -> car.getPrice() <= priceTo)
                            .collect(Collectors.toList());
                    break;
                case "model":
                    cars = cars.stream()
                            .filter(car -> car.getModel().equalsIgnoreCase(value))
                            .collect(Collectors.toList());
                    break;
                case "yearFrom<year<yearTo":
                    String[] yearRange = value.split("<year<");
                    int yearRangeFrom = Integer.parseInt(yearRange[0]);
                    int yearRangeTo = Integer.parseInt(yearRange[1]);
                    cars = cars.stream()
                            .filter(car -> car.getYear() >= yearRangeFrom && car.getYear() <= yearRangeTo)
                            .collect(Collectors.toList());
                    break;
                case "yearFrom<year":
                    String[] yearOnlyFrom = value.split("<year<");
                    int yearFrom = Integer.parseInt(yearOnlyFrom[0]);
                    cars = cars.stream()
                            .filter(car -> car.getYear() >= yearFrom)
                            .collect(Collectors.toList());
                    break;
                case "year<yearTo":
                    String[] yearOnlyTo = value.split("<year<");
                    int yearTo = Integer.parseInt(yearOnlyTo[0]);
                    cars = cars.stream()
                            .filter(car -> car.getYear() <= yearTo)
                            .collect(Collectors.toList());
                    break;
                case "availability":
                    boolean availability = Boolean.parseBoolean(value);
                    cars = cars.stream()
                            .filter(car -> car.isAvailability() == availability)
                            .collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }

        return cars;
    }


    // forming query: brand:toyota;color:white;priceFrom<price<priceTo;model:xr200;yearFrom<2000<yearTo;availability:true
    public String formingQuerySearchCars (){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input parameters (if field doesn't need leave it blank)");

        System.out.println("Brand (example, toyota): ");
        String brand = scanner.nextLine().trim();

        System.out.println("Color (example, white): ");
        String color = scanner.nextLine().trim();

        System.out.println("Min price (leave it blank, if field doesn't need leave it blank): ");
        String priceFrom = scanner.nextLine().trim();

        System.out.println("Max price (leave it blank, if field doesn't need leave it blank): ");
        String priceTo = scanner.nextLine().trim();

        System.out.println("Model (leave it blank, if field doesn't need leave it blank): : ");
        String model = scanner.nextLine().trim();

        System.out.println("Year from (оставьте пустым, если не нужно): ");
        String yearFrom = scanner.nextLine().trim();

        System.out.println("Year to (leave it blank, if field doesn't need leave it blank): ");
        String yearTo = scanner.nextLine().trim();

        System.out.println("Availability (true/false, оставьте пустым, если не нужно): ");
        String availability = scanner.nextLine().trim();

        // Создание строки запроса
        StringBuilder queryBuilder = new StringBuilder();

        if (!brand.isEmpty()) {
            queryBuilder.append("brand:").append(brand).append(";");
        }

        if (!color.isEmpty()) {
            queryBuilder.append("color:").append(color).append(";");
        }

        if (!priceFrom.isEmpty() && !priceTo.isEmpty()) {
            queryBuilder.append("priceFrom<price<priceTo:").append(priceFrom).append("<price<").append(priceTo).append(";");
        } else if (!priceFrom.isEmpty()) {
            queryBuilder.append("priceFrom<price:").append(priceFrom).append(";");
        } else if (!priceTo.isEmpty()) {
            queryBuilder.append("price<priceTo:").append(priceTo).append(";");
        }

        if (!model.isEmpty()) {
            queryBuilder.append("model:").append(model).append(";");
        }

        if (!yearFrom.isEmpty() && !yearTo.isEmpty()) {
            queryBuilder.append("yearFrom<year<yearTo:").append(yearFrom).append("<year<").append(yearTo).append(";");
        } else if (!yearFrom.isEmpty()) {
            queryBuilder.append("yearFrom<year:").append(yearFrom).append(";");
        } else if (!yearTo.isEmpty()) {
            queryBuilder.append("year<yearTo:").append(yearTo).append(";");
        }

        if (!availability.isEmpty()) {
            queryBuilder.append("availability:").append(availability).append(";");
        }

        // Удаление последнего символа ';' если строка не пустая
        String query = queryBuilder.length() > 0 ? queryBuilder.substring(0, queryBuilder.length() - 1) : "";

        return query;

    }

}
