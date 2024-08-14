package com.backend.view;

import com.backend.model.Car;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.UserRepository;
import com.backend.service.CarService;
import com.backend.util.ErrorResponses;
import com.backend.util.SuccessResponses;

import java.util.List;
import java.util.Scanner;

public abstract class MenuHolder {

    static final Scanner scanner = new Scanner(System.in);
    public abstract void showMainMenu();
    public abstract void handleLogging();
    protected UserRepository userRepository = new UserRepository();
    protected CarRepository carRepository = new CarRepository();

    CarService carService = new CarService();

    public Car selectCar() {
        System.out.println("Available cars:");
        carService.getAvailableCars().forEach(System.out::println);
        System.out.println("Enter the ID of the car you want:");
        String carIdStr = scanner.nextLine();
        int carId;
        try {
            carId = Integer.parseInt(carIdStr);
        } catch (ClassCastException e) {
            ErrorResponses.printCustomMessage("Invalid car ID format.");
            return null;
        }
        return carRepository.findById(carId);
    }


    public String getNewField(String field) {
        System.out.println("Enter new "+field+" (leave blank to keep unchanged): ");
        return scanner.nextLine();
    }

    public String getField(String field){
        System.out.println("Enter "+field + ":");
        String value = scanner.nextLine();
        if (value.isEmpty()) {
            ErrorResponses.printCustomMessage("Field cannot be empty.");
            System.out.println("Enter "+field + ":");
            value = scanner.nextLine();
        }
        return value;
    }

    boolean confirm(String message) {
        while (true) {
            System.out.println("Input yes/y or not/n");
            String answer = scanner.nextLine().trim();
            if (answer.equals("yes") || answer.equals("y")) {
                SuccessResponses.printCustomMessage("Okay, as you wish my friend...");
                SuccessResponses.printCustomMessage(message);
                return true;
            } else if (answer.equals("no") || answer.equals("n")) {
                SuccessResponses.printCustomMessage("\uD83E\uDD1D Okay, keep this");
                return false;
            } else {
                ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

    boolean checkForExit(String message) {
        if (message.equals("exit")) return false;
        return false;
    }

    protected void printResult(List<? extends Object> list) {
        for (Object entity : list) {
            System.out.println(entity);
        }
    }

}
