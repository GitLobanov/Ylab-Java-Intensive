package com.backend.util.menu;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.CarRepository;
import com.backend.repository.UserRepository;
import com.backend.service.user.AdminService;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.SuccessResponses;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class MenuHolderAdmin extends MenuHolder {

    private AdminService adminService;


    public MenuHolderAdmin() {
        adminService = new AdminService();
    }

    @Override
    public void showMainMenu() {
        System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Main Menu ===" + ConsoleColors.RESET);
        System.out.println("\uD83D\uDE97 Type 'c/cars' to manage cars.");
        System.out.println("\uD83D\uDE97 Type 'r/requests' to manage cars.");
        System.out.println("\uD83D\uDCE6 Type 'o/orders' to manage orders.");
        System.out.println("\uD83D\uDC69\uD83C\uDFFB\u200D\uD83D\uDCBC Type 'e/employees' to manage employees.");
        System.out.println("\uD83D\uDD10 Type 'l/logout' to logout from account.");
        System.out.println("\uD83D\uDD1A Type 'end' to quit the application.");
        System.out.print("Enter your choice: ");
    }

    @Override
    public void handleCars() {

        while (true) {
            System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Cars Menu ===" + ConsoleColors.RESET);
            System.out.println("1\uFE0F⃣ View all cars");
            System.out.println("2\uFE0F⃣ Search cars");
            System.out.println("3\uFE0F⃣ Add a new car");
            System.out.println("4\uFE0F⃣ Update a car");
            System.out.println("5\uFE0F⃣ Delete a car");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    adminService.viewAllCars();
                    break;

                case "2":
                    String query = adminService.formingQuerySearchCars();
                    adminService.searchCars(query);
                    break;

                case "3":
                    addCarConsole();
                    break;

                case "4":
                    updateCarConsole();
                    break;

                case "5":
                    deleteCarConsole();
                    break;
                case "back":
                    return;

                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

    public void handleServiceRequests() {
        while (true) {
            System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Requests to service Menu ===" + ConsoleColors.RESET);
            System.out.println("1\uFE0F⃣ View all requests");
            System.out.println("2\uFE0F⃣ Search requests");
            System.out.println("3\uFE0F⃣ Create a new request");
            System.out.println("4\uFE0F⃣ Update an request");
            System.out.println("5\uFE0F⃣ Cancel an request");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    adminService.viewAllServiceOrders();
                    break;

                case "2":
                    String query = adminService.formingQuerySearchOrders(Order.TypeOrder.SERVICE);
                    adminService.searchOrders(query);
                    break;

                case "3":
                    addRequestConsole();
                    break;

                case "4":
                    updateRequest();
                    break;

                case "5":
                    cancelRequest();
                    break;
                case "back":
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }


    public void handleOrders() {
        while (true) {
            System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Orders Menu ===" + ConsoleColors.RESET);
            System.out.println("1\uFE0F⃣ View all orders");
            System.out.println("2\uFE0F⃣ Search order");
            System.out.println("3\uFE0F⃣ Create a new order");
            System.out.println("4\uFE0F⃣ Update an order");
            System.out.println("5\uFE0F⃣ Cancel an order");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    adminService.viewAllBuyingOrders();
                    break;

                case "2":
                    String query = adminService.formingQuerySearchOrders(Order.TypeOrder.BUYING);
                    adminService.searchOrders(query);
                    break;

                case "3":
                    addOrderConsole(Order.TypeOrder.BUYING);
                    break;

                case "4":
                    updateOrderConsole();
                    break;

                case "5":
                    cancelOrder();
                    break;
                case "back":
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

    public void handleEmployees() {
        while (true) {
            System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Employees Menu ===" + ConsoleColors.RESET);
            System.out.println("1\uFE0F⃣  View all employees");
            System.out.println("2\uFE0F⃣ Add a new employee");
            System.out.println("3\uFE0F⃣ Update an employee");
            System.out.println("4\uFE0F⃣ Remove an employee");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    adminService.viewAllEmployees();
                    break;
                case "2":
                    addEmployeeConsole();
                    break;
                case "3":
                    updateEmployeeConsole();
                    break;
                case "4":
                    removeEmployeeConsole();
                    break;
                case "back":
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

    // cars

    public void addCarConsole() {
        System.out.println("\uD83C\uDD95 Create a new car");

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
    }

    public void updateCarConsole() {
        System.out.println("\uD83C\uDD99 Update a car");

        System.out.println("Input please, id of car you want to update: ");
        String inputUpdateId = scanner.nextLine().trim();
        Car carForUpdate = adminService.findCarById(UUID.fromString(inputUpdateId));

        System.out.println("\uD83D\uDC40 You sure, what do you want update this car :");
        System.out.println(carForUpdate.toString());

        if (!confirm()) return;

        System.out.println("Input info about car (if you don't wanna update some fields, just leave they empty):");

        System.out.println("color (was " + carForUpdate.getColor() + "): ");
        String colorInput = scanner.nextLine().trim();
        if (!colorInput.isEmpty()) {
            carForUpdate.setColor(colorInput);
        }

        System.out.println("availability (yes/no): ");
        String availabilityUpdateInput = scanner.nextLine().trim();
        if (!availabilityUpdateInput.isEmpty()) {
            carForUpdate.setAvailability(availabilityUpdateInput.equalsIgnoreCase("yes") || availabilityUpdateInput.equalsIgnoreCase("true"));
        }

        System.out.println("model (was " + carForUpdate.getModel() + "): ");
        String modelInput = scanner.nextLine().trim();
        if (!modelInput.isEmpty()) {
            carForUpdate.setModel(modelInput);
        }

        System.out.println("brand (was " + carForUpdate.getBrand() + "): ");
        String brandInput = scanner.nextLine().trim();
        if (!brandInput.isEmpty()) {
            carForUpdate.setBrand(brandInput);
        }

        System.out.println("year (was " + carForUpdate.getYear() + "): ");
        String yearInput = scanner.nextLine().trim();
        if (!yearInput.isEmpty()) {
            int yearUpdate = Integer.parseInt(yearInput);
            carForUpdate.setYear(yearUpdate);
        }

        System.out.println("price (was " + carForUpdate.getPrice() + "): ");
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
        SuccessResponses.printCustomMessage("Car updated successfully.");
    }


    public void deleteCarConsole() {
        System.out.println("Input please, id of car you want to delete: ");
        String inputDeleteId = scanner.nextLine().trim();
        Car carForDeleting = adminService.findCarById(UUID.fromString(inputDeleteId));

        System.out.println("\uD83D\uDC40 You sure, what do you want delete this car :");
        System.out.println(carForDeleting.toString());

        if (confirm()) adminService.deleteCar(carForDeleting);
    }



    // requests

    private void addRequestConsole() {
        addOrderConsole(Order.TypeOrder.SERVICE);
    }

    private void updateRequest() {
        System.out.println("\uD83C\uDD99 Update order");
        System.out.println("Input please, id of order you want to update: ");
        String inputDeleteId = scanner.nextLine().trim();
        Order orderUpdate = adminService.findOrderById(UUID.fromString(inputDeleteId));
        System.out.println("\uD83D\uDC40 You sure, what do you want to update this:");
        System.out.println(orderUpdate);
        if (!confirm()) return;
        updateOrderConsole();
    }

    private void cancelRequest() {
        System.out.println("Input please, id of request you want to cancel: ");
        String inputCancelId = scanner.nextLine().trim();
        Order orderCancel = adminService.findOrderById(UUID.fromString(inputCancelId));
        System.out.println("\uD83D\uDC40 You sure, what do you want cancel this:");
        System.out.println(orderCancel);
        if (!confirm()) return;
        adminService.cancelOrder(orderCancel);
    }

    // orders

    private void addOrderConsole(Order.TypeOrder typeOrder) {

        System.out.println("\uD83C\uDD95 Create a new order:");
        Car car = selectCar();
        while (car == null) {
            ErrorResponses.printCustomMessage("Car selection failed.");
            System.out.println("\uD83C\uDD95 Create a new order:");
            car = selectCar();
        }

        User user = selectUser();
        while (user == null) {
            ErrorResponses.printCustomMessage("User selection failed.");
            user = selectUser();
        }

        System.out.println("Enter a note for the order:");
        String note = scanner.nextLine();

        Order order = new Order(car, user, typeOrder, note);

        SuccessResponses.printCustomMessage("Order created successfully: " + order);
    }

    private void updateOrderConsole() {

        System.out.println("\uD83C\uDD99 Update order");
        System.out.println("Input please, id of order you want to update: ");
        String inputDeleteId = scanner.nextLine().trim();
        Order order = adminService.findOrderById(UUID.fromString(inputDeleteId));
        System.out.println("\uD83D\uDC40 You sure, what do you want to update this:");
        System.out.println(order);
        if (!confirm()) return;

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
                    ErrorResponses.printCustomMessage("Car selection failed.");
                }
            } catch (IllegalArgumentException e) {
                ErrorResponses.printCustomMessage("Invalid car ID format.");
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
                    ErrorResponses.printCustomMessage("Invalid type of order.");
                }
            } catch (NumberFormatException e) {
                ErrorResponses.printCustomMessage("Invalid type of order format.");
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
                    ErrorResponses.printCustomMessage("Invalid status of order.");
                }
            } catch (NumberFormatException e) {
                ErrorResponses.printCustomMessage("Invalid status of order format.");
            }
        }

        adminService.updateOrder(order);

        SuccessResponses.printCustomMessage("Order updated successfully.");
    }

    private void cancelOrder(){
        System.out.println("Input please, id of order you want to cancel: ");
        String inputCancelId = scanner.nextLine().trim();
        Order orderCancel = adminService.findOrderById(UUID.fromString(inputCancelId));
        System.out.println("\uD83D\uDC40 You sure, what do you want cancel this:");
        System.out.println(orderCancel);
        if (!confirm()) return;
        adminService.cancelOrder(orderCancel);
    }


    // employees

    private void addEmployeeConsole() {
        System.out.println("Enter username: ");
        String userName = scanner.nextLine();

        while (UserRepository.getInstance().findByUserName(userName) != null) {
            ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_USERNAME_ALREADY_EXIST);
            System.out.println("Enter username: ");
            userName = scanner.nextLine();
        }

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        System.out.println("Enter role (ADMIN/MANAGER): ");

        User.Role role = null;
        while (role == null) {
            System.out.print("Enter role (ADMIN/MANAGER): ");
            try {
                role = User.Role.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                ErrorResponses.printCustomMessage("User role must be ADMIN or MANAGER.");
            }
        }

        System.out.println("Enter name: ");
        String name = scanner.nextLine();

        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Enter phone: ");
        String phone = scanner.nextLine();

        User employee = new User(userName, password, role, name, email, phone);
        if (adminService.addEmployee(employee)) {
            SuccessResponses.printCustomMessage("Employee added successfully.");
        }
    }

    private void updateEmployeeConsole() {
        System.out.println("Enter username of the employee to update: ");
        String userName = scanner.nextLine();

        if (UserRepository.getInstance().findByUserName(userName)==null) {
            ErrorResponses.printCustomMessage("Employee not found.");
            return;
        }

        System.out.println("Enter new password (leave blank to keep unchanged): ");
        String password = scanner.nextLine();

        System.out.println("Enter new name (leave blank to keep unchanged): ");
        String name = scanner.nextLine();

        System.out.println("Enter new email (leave blank to keep unchanged): ");
        String email = scanner.nextLine();

        System.out.println("Enter new phone (leave blank to keep unchanged): ");
        String phone = scanner.nextLine();

        User existingEmployee = UserRepository.getInstance().findByUserName(userName);
        User updatedEmployee = new User(
                userName,
                password.isEmpty() ? existingEmployee.getPassword() : password,
                existingEmployee.getRole(),
                name.isEmpty() ? existingEmployee.getName() : name,
                email.isEmpty() ? existingEmployee.getEmail() : email,
                phone.isEmpty() ? existingEmployee.getPhone() : phone
        );

        if (adminService.updateEmployee(userName, updatedEmployee)) {
            SuccessResponses.printCustomMessage("Employee updated successfully.");
        }
    }

    private void removeEmployeeConsole() {
        System.out.println("Enter username of the employee to remove: ");
        String userName = scanner.nextLine();

        if (adminService.removeEmployee(userName)) {
            SuccessResponses.printCustomMessage("Employee removed successfully.");
        }
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
            ErrorResponses.printCustomMessage("Invalid car ID format.");
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
            ErrorResponses.printCustomMessage("Invalid user ID format.");
            return null;
        }
        return UserRepository.getInstance().findById(userId);
    }


}
