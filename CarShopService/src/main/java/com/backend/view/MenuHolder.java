package com.backend.view;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.repository.CarRepository;
import com.backend.service.user.parent.UserAbstractService;
import com.backend.util.ErrorResponses;
import com.backend.util.SuccessResponses;

import java.util.Scanner;
import java.util.UUID;

public abstract class MenuHolder {

    static final Scanner scanner = new Scanner(System.in);
    public abstract void showMainMenu();
    public abstract void handleLogging();

    public Car selectCar() {
        System.out.println("Available cars:");
        UserAbstractService.viewNotOrderedCars();
        System.out.println("Enter the ID of the car you want to order:");
        String carIdStr = scanner.nextLine();
        UUID carId;
        try {
            carId = UUID.fromString(carIdStr);
        } catch (IllegalArgumentException e) {
            ErrorResponses.printCustomMessage("Invalid car ID format.");
            return null;
        }
        return CarRepository.getInstance().findById(carId);
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

}
