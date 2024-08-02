package com.backend.controller;

import com.backend.model.Car;
import com.backend.service.user.AdminService;
import com.backend.service.user.ClientService;
import com.backend.service.user.ManagerService;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;

import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class AdminController {

    private static final Scanner scanner = new Scanner(System.in);

    private AdminService adminService = new AdminService();

    public void start () {
        System.out.println("Hello, " + Session.getInstance().getUser().getName());
        while (true) {
            showMainMenu();
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "cars":
                    handleCars();
                    break;
                case "orders":
//                    handleOrders();
                    break;
                case "employees":
//                    handleEmployees();
                    break;
                case "exit":
                    Session.getInstance().setStage(Session.Stage.EXIT);
                    return;
                default:
                    String message = ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND.get(new Random().nextInt(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND.size()));
                    System.out.println(message);
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("Type 'cars' to manage cars.");
        System.out.println("Type 'orders' to manage orders.");
        System.out.println("Type 'employees' to manage employees.");
        System.out.println("Type 'exit' to quit the application.");
        System.out.print("Enter your choice: ");
    }

    private void handleCars() {
        System.out.println("\n=== Cars Menu ===");
        System.out.println("1. View all cars");
        System.out.println("2. Add a new car");
        System.out.println("3. Update a car");
        System.out.println("4. Delete a car");
        System.out.println("Type 'back' to return to the main menu.");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim().toLowerCase();

        switch (choice) {
            case "1":
                adminService.viewAllCars();
                break;
            case "2":

                System.out.println("color: ");
                String color = scanner.nextLine().trim();
                System.out.println("availability: ");
                String availabilityInput = scanner.nextLine().trim();
                boolean availability = availabilityInput.equals("yes") || availabilityInput.equals("true");
                System.out.println("model: ");
                String model = scanner.nextLine().trim();
                System.out.println("brand: ");
                String brand = scanner.nextLine().trim();
                System.out.println("year: ");
                int year = Integer.parseInt(scanner.nextLine().trim());
                System.out.println("price: ");
                double price = Double.parseDouble(scanner.nextLine().trim());
                System.out.println("condition: ");
                String condition = scanner.nextLine().trim();

                Car car = new Car(brand, model, year, price, condition, color, availability);

                adminService.addCar(car);

                System.out.println("Woo! We have new car!");
                break;
            case "3":

                System.out.println("Input please, id of car you want to update: ");
                String inputUpdateId = scanner.nextLine().trim();
                Car carForUpdate = adminService.findCarById(UUID.fromString(inputUpdateId));

                System.out.println("You sure, what do you want update this car :");
                System.out.println(carForUpdate.toString());

                if (!comfirm()) break;


                System.out.println("Input info about car (if you don't wanna update some fields, just leave they empty):");

                System.out.println("color (was "+ carForUpdate.getColor() + "): ");
                String colorInput = scanner.nextLine().trim();
                if (!colorInput.isEmpty()) {
                    carForUpdate.setColor(colorInput);
                }

                System.out.println("availability (yes/no): ");
                String availabilityUpdateInput = scanner.nextLine().trim();
                if (!availabilityUpdateInput.isEmpty()) {
                    carForUpdate.setAvailability(availabilityUpdateInput.equalsIgnoreCase("yes") || availabilityUpdateInput.equalsIgnoreCase("true"));
                }

                System.out.println("model (was "+ carForUpdate.getModel() + "): ");
                String modelInput = scanner.nextLine().trim();
                if (!modelInput.isEmpty()) {
                    carForUpdate.setModel(modelInput);
                }

                System.out.println("brand (was "+ carForUpdate.getBrand() + "): ");
                String brandInput = scanner.nextLine().trim();
                if (!brandInput.isEmpty()) {
                    carForUpdate.setBrand(brandInput);
                }

                System.out.println("year (was "+ carForUpdate.getYear() + "): ");
                String yearInput = scanner.nextLine().trim();
                if (!yearInput.isEmpty()) {
                    int yearUpdate = Integer.parseInt(yearInput);
                    carForUpdate.setYear(yearUpdate);
                }

                System.out.println("price (was "+ carForUpdate.getPrice() + "): ");
                String priceInput = scanner.nextLine().trim();
                if (!priceInput.isEmpty()) {
                    double priceUpdate = Double.parseDouble(priceInput);
                    carForUpdate.setPrice(priceUpdate);
                }

                System.out.println("condition: ");
                String conditionInput = scanner.nextLine().trim();
                if (!conditionInput.isEmpty()) {
                    carForUpdate.setCondition(conditionInput);
                }

                adminService.updateCar(carForUpdate);

                System.out.println("Yep! Updated!");
                break;
            case "4":
                System.out.println("Input please, id of car you want to delete: ");
                String inputDeleteId = scanner.nextLine().trim();
                Car carForDeleting = adminService.findCarById(UUID.fromString(inputDeleteId));

                System.out.println("You sure, what do you want delete this car :");
                System.out.println(carForDeleting.toString());

                if (comfirm()) adminService.deleteCar(carForDeleting);

                break;
            case "back":
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private boolean comfirm() {
        while (true) {
            System.out.println("Input yes/y or not/n");
            String answer = scanner.nextLine().trim();
            if (answer.equals("yes") || answer.equals("y")) {
                System.out.println("Okay, as you wish my friend. Buy, buy lovely car...");
                return true;
            } else if (answer.equals("no") || answer.equals("n")) {
                System.out.println("Okay, keep this car");
                return false;
            } else {
                System.out.println(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND.get(new Random().nextInt(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND.size()-1)));
            }
        }

    }

//    private void handleOrders() {
//        System.out.println("\n=== Orders Menu ===");
//        System.out.println("1. View all orders");
//        System.out.println("2. Create a new order");
//        System.out.println("3. Update an order");
//        System.out.println("4. Cancel an order");
//        System.out.println("Type 'back' to return to the main menu.");
//        System.out.print("Enter your choice: ");
//        String choice = scanner.nextLine().trim().toLowerCase();
//
//        switch (choice) {
//            case "1":
//                viewAllOrders();
//                break;
//            case "2":
//                createOrder();
//                break;
//            case "3":
//                updateOrder();
//                break;
//            case "4":
//                cancelOrder();
//                break;
//            case "back":
//                return;
//            default:
//                System.out.println("Invalid choice. Returning to main menu.");
//        }
//    }
//
//    private void handleEmployees() {
//        System.out.println("\n=== Employees Menu ===");
//        System.out.println("1. View all employees");
//        System.out.println("2. Add a new employee");
//        System.out.println("3. Update an employee");
//        System.out.println("4. Remove an employee");
//        System.out.println("Type 'back' to return to the main menu.");
//        System.out.print("Enter your choice: ");
//        String choice = scanner.nextLine().trim().toLowerCase();
//
//        switch (choice) {
//            case "1":
//                viewAllEmployees();
//                break;
//            case "2":
//                addEmployee();
//                break;
//            case "3":
//                updateEmployee();
//                break;
//            case "4":
//                removeEmployee();
//                break;
//            case "back":
//                return;
//            default:
//                System.out.println("Invalid choice. Returning to main menu.");
//        }
//    }

}
