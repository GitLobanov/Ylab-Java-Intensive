package com.backend.controller;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.CarRepository;
import com.backend.repository.OrderRepository;
import com.backend.repository.UserRepository;
import com.backend.service.user.AdminService;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;

import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class AdminController {

    private static final Scanner scanner = new Scanner(System.in);

    private AdminService adminService = new AdminService();

    public void start () {
        System.out.println("Admen is HERE!");
        System.out.println("Hello, " + Session.getInstance().getUser().getName() + " \uD83D\uDC51");
        while (true) {
            showMainMenu();
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "cars":
                    handleCars();
                    break;
                case "orders":
                    handleOrders();
                    break;
                case "employees":
//                    handleEmployees();
                    break;
                case "end":
                    Session.getInstance().setStage(Session.Stage.EXIT);
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("\uD83D\uDE97 Type 'cars' to manage cars.");
        System.out.println("\uD83D\uDCE6 Type 'orders' to manage orders.");
        System.out.println("\uD83D\uDC69\uD83C\uDFFB\u200D\uD83D\uDCBC Type 'employees' to manage employees.");
        System.out.println("\uD83D\uDD10 Type 'logout' to logout from account.");
        System.out.println("\uD83D\uDD1A Type 'end' to quit the application.");
        System.out.print("Enter your choice: ");
    }

    private void handleCars() {
        System.out.println("\n=== Cars Menu ===");
        System.out.println("1️⃣ View all cars");
        System.out.println("2️⃣ Add a new car");
        System.out.println("3️⃣ Update a car");
        System.out.println("4️⃣ Delete a car");
        System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
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

                if (!confirm()) break;


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

                if (confirm()) adminService.deleteCar(carForDeleting);

                break;

            case "back":
                return;

            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private void handleOrders() {
        System.out.println("\n=== Orders Menu ===");
        System.out.println("1️⃣ View all orders");
        System.out.println("2️⃣ Create a new order");
        System.out.println("3️⃣ Update an order");
        System.out.println("4️⃣ Cancel an order");
        System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim().toLowerCase();

        switch (choice) {
            case "1":
                adminService.viewAllOrders();
                break;
            case "2":
                Order order = createOrder();
                if (order != null) {
                    System.out.println("Order created successfully: " + order);
                } else {
                    System.out.println("Order creation failed.");
                }
                adminService.createOrder(order);
                break;

            case "3":
                adminService.viewAllOrders();
                System.out.println("Input please, id of order you want to update: ");
                String inputDeleteId = scanner.nextLine().trim();
                Order orderUpdate = adminService.findOrderById(UUID.fromString(inputDeleteId));
                System.out.println("You sure, what do you want update this:");
                System.out.println(orderUpdate);
                if (!confirm()) break;
                updateOrder(orderUpdate);
                break;

            case "4":
                System.out.println("Input please, id of order you want to cancel: ");
                String inputCancelId = scanner.nextLine().trim();
                Order orderCancel = adminService.findOrderById(UUID.fromString(inputCancelId));
                System.out.println("You sure, what do you want cancel this:");
                System.out.println(orderCancel);
                if (!confirm()) break;
                adminService.cancelOrder(orderCancel);
                break;
            case "back":
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

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

    private Order createOrder() {
        System.out.println("Create a new order:");

        // Select car
        Car car = selectCar();
        if (car == null) {
            System.out.println("Car selection failed.");
            return null;
        }

        // Select user
        User user = selectUser();
        if (user == null) {
            System.out.println("User selection failed.");
            return null;
        }

        // Enter a note
        System.out.println("Enter a note for the order:");
        String note = scanner.nextLine();

        // Create and return the order
        return new Order(car, user, Order.TypeOrder.BUYING, note);
    }

    private void updateOrder(Order order) {

        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        System.out.println("Updating order: " + order);

        // Update car
        System.out.println("Current car: " + order.getCar());
        System.out.println("Enter new car ID or press Enter to skip:");
        String carIdStr = scanner.nextLine();
        if (!carIdStr.isEmpty()) {
            UUID carId;
            try {
                carId = UUID.fromString(carIdStr);
                Car newCar = adminService.findCarById(carId);
                if (newCar != null) {
                    order.setCar(newCar);
                } else {
                    System.out.println("Car not found.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid car ID format.");
            }
        }

        // Update type of order
        System.out.println("Current type of order: " + order.getType());
        System.out.println("Enter new type of order or press Enter to skip:");
        for (Order.TypeOrder type : Order.TypeOrder.values()) {
            System.out.println(type.ordinal() + ": " + type);
        }
        String typeIndexStr = scanner.nextLine();
        if (!typeIndexStr.isEmpty()) {
            try {
                int typeIndex = Integer.parseInt(typeIndexStr);
                if (typeIndex >= 0 && typeIndex < Order.TypeOrder.values().length) {
                    order.setType(Order.TypeOrder.values()[typeIndex]);
                } else {
                    System.out.println("Invalid type of order.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid type of order format.");
            }
        }

        // Update note
        System.out.println("Current note: " + order.getNote());
        System.out.println("Enter new note or press Enter to skip:");
        String note = scanner.nextLine();
        if (!note.isEmpty()) {
            order.setNote(note);
        }

        // Update status
        System.out.println("Current status of order: " + order.getStatus());
        System.out.println("Enter new status of order or press Enter to skip:");
        for (Order.OrderStatus status : Order.OrderStatus.values()) {
            System.out.println(status.ordinal() + ": " + status);
        }
        String statusIndexStr = scanner.nextLine();
        if (!statusIndexStr.isEmpty()) {
            try {
                int statusIndex = Integer.parseInt(statusIndexStr);
                if (statusIndex >= 0 && statusIndex < Order.OrderStatus.values().length) {
                    order.setStatus(Order.OrderStatus.values()[statusIndex]);
                } else {
                    System.out.println("Invalid status of order.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid status of order format.");
            }
        }

        adminService.updateOrder(order);
    }

    private Car selectCar() {
        System.out.println("Available cars:");
        adminService.viewAllCars();
        System.out.println("Enter the ID of the car you want to order:");
        String carIdStr = scanner.nextLine();
        UUID carId;
        try {
            carId = UUID.fromString(carIdStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid car ID format.");
            return null;
        }
        return CarRepository.getInstance().findById(carId);
    }

    private User selectUser() {
        System.out.println("Available users:");
        adminService.viewAllClients();
        System.out.println("Enter the ID of the user:");
        String userIdStr = scanner.nextLine();
        UUID userId;
        try {
            userId = UUID.fromString(userIdStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid user ID format.");
            return null;
        }
        return UserRepository.getInstance().findById(userId);
    }


    private boolean confirm() {
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
                ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

}
