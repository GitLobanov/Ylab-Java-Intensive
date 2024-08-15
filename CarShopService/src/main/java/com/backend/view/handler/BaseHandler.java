package com.backend.view.handler;

import com.backend.model.Car;
import com.backend.model.User;
import com.backend.service.CarService;
import com.backend.service.impl.ClientService;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.SuccessResponses;

import java.util.List;
import java.util.Scanner;

public abstract class BaseHandler{

    public abstract void handlerMenu();
    public abstract void create();
    public abstract void update();
    public abstract void delete();
    public abstract void search();
    public abstract void viewAll();

    Scanner scanner = new Scanner(System.in);

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

    public void printList(List<? extends Object> list) {
        list.forEach(System.out::println);
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

    public Car selectCar() {
        System.out.println("Available cars:");
        CarService carService = new CarService();
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
        return carService.getCarById(carId);
    }

    public String getNewField(String field) {
        System.out.println("Enter new "+field+" (leave blank to keep unchanged): ");
        return scanner.nextLine();
    }

    public User selectClient() {
        ClientService clientService = new ClientService();

        System.out.println("Available clients:");
        printList(clientService.getAllClients());
        System.out.println("Enter the username of the client:");
        String userUsernameStr = scanner.nextLine();

        User user = clientService.getClientByUsername(userUsernameStr);

        if (user==null){
            System.out.println(ConsoleColors.YELLOW_BOLD + "Client not found." + ConsoleColors.RESET);
            return null;
        } else {
            return user;
        }
    }

    boolean checkForExit(String message) {
        if (message.equals("exit")) return false;
        return false;
    }

}
