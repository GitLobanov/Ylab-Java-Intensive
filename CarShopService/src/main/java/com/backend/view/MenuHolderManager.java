package com.backend.view;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.service.impl.ManagerService;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.util.SuccessResponses;

import java.util.UUID;

public class MenuHolderManager extends MenuHolder{

    private ManagerService managerService;


    public MenuHolderManager() {
        managerService = new ManagerService();
    }

    @Override
    public void showMainMenu() {
        System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Main Menu ===" + ConsoleColors.RESET);
        System.out.println("\uD83D\uDE97 Type 'c/cars' to manage cars.");
        System.out.println("\uD83D\uDE97 Type 'r/requests' to manage cars.");
        System.out.println("\uD83D\uDCE6 Type 'o/orders' to manage orders.");
        System.out.println("\uD83D\uDC68\uD83C\uDFFB\u200D⚖\uFE0F Type 'cl/clients' to manage clients.");
        System.out.println("\uD83D\uDDC3\uFE0F Type 'a/actions' to manage actions.");
        System.out.println("\uD83D\uDD10 Type 'l/logout' to logout from account.");
        System.out.println("\uD83D\uDD1A Type 'exit' to quit the application.");
        System.out.print("Enter your choice: ");
    }

    @Override
    public void handleLogging() {
        while (true) {
            System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Actions Menu ===" + ConsoleColors.RESET);
            System.out.println("1\uFE0F⃣ View my actions");
            System.out.println("2\uFE0F⃣ Filter actions");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    managerService.viewMyActionLog(Session.getInstance().getUser());
                    break;
                case "2":
                    String query = managerService.formingQuerySearchMyActionLogs();
                    managerService.searchMyActionLog(query, Session.getInstance().getUser());
                    break;
                case "back":
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

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
                    managerService.viewAllCars();
                    break;

                case "2":
                    String query = managerService.formingQuerySearchCars();
                    managerService.searchCars(query);
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
            System.out.println("3\uFE0F⃣ View my taken requests");
            System.out.println("4\uFE0F⃣ Create a new request");
            System.out.println("5\uFE0F⃣ Take request");
            System.out.println("6\uFE0F⃣ Update an request");
            System.out.println("7\uFE0F⃣ Cancel an request");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    managerService.viewAllServiceOrders();
                    break;

                case "2":
                    String query = managerService.formingQuerySearchOrders(Order.TypeOrder.SERVICE);
                    managerService.searchOrders(query);
                    break;

                case "3":
                    addRequestConsole();
                    break;

                case "4":
                    managerService.viewMyTakenRequests(Session.getInstance().getUser());
                    break;

                case "5":
                    takeRequestConsole();
                    break;

                case "6":
                    updateRequest();
                    break;

                case "7":
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
            System.out.println("3\uFE0F⃣ View my taken requests");
            System.out.println("4\uFE0F⃣ Create a new order");
            System.out.println("5\uFE0F⃣ Take order");
            System.out.println("6\uFE0F⃣ Update an order");
            System.out.println("7\uFE0F⃣ Cancel an order");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    managerService.viewAllBuyingOrders();
                    break;

                case "2":
                    String query = managerService.formingQuerySearchOrders(Order.TypeOrder.BUYING);
                    managerService.searchOrders(query);
                    break;

                case "3":
                    managerService.viewMyTakenOrders(Session.getInstance().getUser());
                    break;

                case "4":
                    addOrderConsole(Order.TypeOrder.BUYING);
                    break;

                case "5":
                    takeOrderConsole();
                    break;

                case "6":
                    updateOrderConsole();
                    break;

                case "7":
                    cancelOrder();
                    break;
                case "back":
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

    public void handleClients (){
        while (true) {
            System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Clients Menu ===" + ConsoleColors.RESET);
            System.out.println("1\uFE0F⃣ View all clients");
            System.out.println("2\uFE0F⃣ Search clients");
            System.out.println("3\uFE0F⃣ View my clients");
            System.out.println("4\uFE0F⃣ Add a new client");
            System.out.println("5\uFE0F⃣ Update an client");
            System.out.println("6\uFE0F⃣ Remove an client");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    managerService.viewAllClients();
                    break;
                case "2":
                    searchClientConsole();
                    break;
                case "3":
                    managerService.viewMyClients(Session.getInstance().getUser());
                    break;
                case "4":
                    addClientConsole();
                    break;
                case "5":
                    updateClientConsole();
                    break;
                case "6":
                    removeClientConsole();
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

        String color = getField("Color: ");
        String availabilityInput = getField("Availability (yes/no): ");
        boolean availability = availabilityInput.equals("yes") || availabilityInput.equals("true");
        String model = getField("Model: ");
        String brand = getField("Brand: ");
        int year = Integer.parseInt(getField("Year (yyyy): "));
        double price = Double.parseDouble(getField("Price: "));
        String condition = getField("Condition: ");

        Car car = new Car(0, brand, model, year, price, condition, color, availability);

        managerService.addCar(car);
        SuccessResponses.printCustomMessage("Woo! We have new car!");
    }

    public void updateCarConsole() {
        System.out.println("\uD83C\uDD99 Update a car");

        Car carForUpdate = selectCar();

        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want update this car :");
        System.out.println(carForUpdate.toString() + ConsoleColors.RESET);

        if (!confirm("Beginning update info...")) return;

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

        managerService.updateCar(carForUpdate);
        SuccessResponses.printCustomMessage("Car updated successfully.");
    }


    public void deleteCarConsole() {
        System.out.println("Input please, id of car you want to delete: ");
        String inputDeleteId = scanner.nextLine().trim();
        Car carForDeleting = managerService.findCarById(Integer.parseInt(inputDeleteId));

        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want delete this car :");
        System.out.println(carForDeleting.toString() + ConsoleColors.RESET);

        if (confirm("Car deleted.")) managerService.deleteCar(carForDeleting);
    }



    // requests

    private void addRequestConsole() {
        System.out.println("\uD83C\uDD95 Create a new request:");
        Car car = selectCar();
        if (car == null) {
            ErrorResponses.printCustomMessage("Car selection failed.");
            return;
        }

        User user = selectClient();
        while (user == null) {
            ErrorResponses.printCustomMessage("User selection failed.");
            user = selectClient();
        }

        System.out.println("Enter a note for the request:");
        String note = scanner.nextLine();

        Order order = new Order(0, car, user, Order.TypeOrder.SERVICE, note);

        managerService.addOrder(order);

        SuccessResponses.printCustomMessage("Order created successfully: " + order);
    }

    private void takeRequestConsole () {
        System.out.println("\uD83C\uDD99 Taking request");
        System.out.println("Input please, id of request you want to take: ");
        String inputDeleteId = scanner.nextLine().trim();
        Order requestForTake = managerService.findOrderById(Integer.parseInt(inputDeleteId));
        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want to take this:");
        System.out.println(requestForTake + ConsoleColors.RESET);
        if (!confirm("Request taken")) return;
        requestForTake.setManager(Session.getInstance().getUser());
        managerService.updateOrder(requestForTake);
    }

    private void updateRequest() {
        System.out.println("\uD83C\uDD99 Update request");
        System.out.println("Input please, id of order you want to update: ");
        String inputDeleteId = scanner.nextLine().trim();
        Order requestUpdate = managerService.findOrderById(Integer.parseInt(inputDeleteId));
        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want to update this:");
        System.out.println(requestUpdate + ConsoleColors.RESET);
        if (!confirm("Request updated")) return;
        updateOrderConsole();
    }

    private void cancelRequest() {
        System.out.println("Input please, id of request you want to cancel: ");
        String inputCancelId = scanner.nextLine().trim();
        Order orderCancel = managerService.findOrderById(Integer.parseInt(inputCancelId));
        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want cancel this:");
        System.out.println(orderCancel + ConsoleColors.RESET);
        if (!confirm("Request canceled.")) return;
        managerService.cancelOrder(orderCancel);
    }

    // orders

    protected void addOrderConsole(Order.TypeOrder typeOrder) {

        System.out.println("\uD83C\uDD95 Create a new order:");
        Car car = selectCar();
        while (car == null) {
            ErrorResponses.printCustomMessage("Car selection failed.");
            System.out.println("\uD83C\uDD95 Create a new order:");
            car = selectCar();
        }

        User user = selectClient();
        while (user == null) {
            ErrorResponses.printCustomMessage("User selection failed.");
            user = selectClient();
        }

        System.out.println("Enter a note for the order:");
        String note = scanner.nextLine();

        Order order = new Order(0, car, user, typeOrder, note);

        managerService.addOrder(order);

        SuccessResponses.printCustomMessage("Order created successfully: " + order);
    }

    private void takeOrderConsole () {
        System.out.println("\uD83C\uDD99 Taking order");
        System.out.println("Input please, id of order you want to take: ");
        String inputDeleteId = scanner.nextLine().trim();
        Order orderForTake = managerService.findOrderById(Integer.parseInt(inputDeleteId));
        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want to take this:");
        System.out.println(orderForTake + ConsoleColors.RESET);
        if (!confirm("Order taken")) return;
        orderForTake.setManager(Session.getInstance().getUser());
        managerService.updateOrder(orderForTake);
    }

    private void updateOrderConsole() {

        Order order = null;
        while (order == null) {
            System.out.println("Input please, id of order you want to update: ");
            String inputDeleteId = scanner.nextLine().trim();
            if (checkForExit(inputDeleteId)) return;
            try {
                order = managerService.findOrderById(Integer.parseInt(inputDeleteId));
                System.out.println();
            } catch (Exception e) {
                ErrorResponses.printCustomMessage("Hey what's wrong with ID, I cannot find by request");
                ErrorResponses.printCustomMessage("Type 'exit', to end process");
            }
        }

        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want to update this:");
        System.out.println(order + ConsoleColors.RESET);

        if (!confirm("Updating order....")) return;

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
            int carId;
            try {
                carId = Integer.parseInt(carIdStr);
                Car newCar = managerService.findCarById(carId);
                if (newCar != null) {
                    order.setCar(newCar);
                } else {
                    ErrorResponses.printCustomMessage("Car selection failed.");
                }
            } catch (ClassCastException e) {
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

        managerService.updateOrder(order);

        SuccessResponses.printCustomMessage("Order updated successfully.");
    }

    protected void cancelOrder(){
        System.out.println("Input please, id of order you want to cancel: ");
        String inputCancelId = scanner.nextLine().trim();
        Order orderCancel = managerService.findOrderById(Integer.parseInt(inputCancelId));
        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want cancel this:");
        System.out.println(orderCancel + ConsoleColors.RESET);
        if (!confirm("Order canceled!")) return;
        managerService.cancelOrder(orderCancel);
    }


    // clients

    public void searchClientConsole() {
        String query = managerService.formingQuerySearchClients();
        managerService.searchClients(query);
    }

    private void addClientConsole() {

        String userName = getField("username");

        while (userRepository.findByUserName(userName) != null) {
            ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_USERNAME_ALREADY_EXIST);
            userName = getField("username");
        }

        String password = getField("password");
        User.Role role = User.Role.CLIENT;
        String name = getField("name");
        String email = getField("email");
        String phone = getField("phone");

        User client = new User(0, userName, password, role, name, email, phone);
        if (client!=null) managerService.addClient(client); {
            SuccessResponses.printCustomMessage("Client added successfully. \n" + client);
        }
    }

    private void updateClientConsole() {
        System.out.println("Enter username of the client to update: ");
        String userName = scanner.nextLine();

        if (userRepository.findByUserName(userName)==null) {
            ErrorResponses.printCustomMessage("Client not found.");
            return;
        }

        String password = getNewField("password");
        String name = getNewField("name");
        String email = getNewField("email");
        String phone = getNewField("phone");

        User existingClient = userRepository.findByUserName(userName);
        User updatedEmployee = new User(
                existingClient.getId(),
                userName,
                password.isEmpty() ? existingClient.getPassword() : password,
                existingClient.getRole(),
                name.isEmpty() ? existingClient.getName() : name,
                email.isEmpty() ? existingClient.getEmail() : email,
                phone.isEmpty() ? existingClient.getPhone() : phone
        );

        if (managerService.updateClient(userName, updatedEmployee)) {
            SuccessResponses.printCustomMessage("Client updated successfully.");
        }
    }

    private void removeClientConsole() {
        System.out.println("Enter username of the client to remove: ");
        String userName = scanner.nextLine();

        if (managerService.removeClient(userName)) {
            SuccessResponses.printCustomMessage("Employee removed successfully.");
        }
    }


    private User selectClient() {
        System.out.println("Available clients:");
        managerService.viewAllClients();
        System.out.println("Enter the username of the client:");
        String userUsernameStr = scanner.nextLine();

        User user = userRepository.findByUserName(userUsernameStr);

        if (user==null){
            System.out.println(ConsoleColors.YELLOW_BOLD + "Client not found." + ConsoleColors.RESET);
            return null;
        } else {
            return user;
        }
    }
}
