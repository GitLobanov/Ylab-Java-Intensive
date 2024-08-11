package com.backend.view;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.service.impl.ClientService;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.util.SuccessResponses;

import java.util.UUID;

public class MenuHolderClient extends MenuHolder{

    private ClientService clientService;


    public MenuHolderClient() {
        clientService = new ClientService();
    }

    @Override
    public void showMainMenu() {
        System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Main Menu ===" + ConsoleColors.RESET);
        System.out.println("\uD83D\uDE97 Type 'c/cars' to cars menu.");
        System.out.println("\uD83D\uDE97 Type 'r/requests' to requests menu.");
        System.out.println("\uD83D\uDCE6 Type 'o/orders' to orders menu.");
        System.out.println("\uD83D\uDDC3\uFE0F Type 'a/actions' to manage actions.");
        System.out.println("\uD83D\uDD10 Type 'l/logout' to logout from account.");
        System.out.println("\uD83D\uDD1A Type 'end' to quit the application.");
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
                    clientService.viewMyActionLog(Session.getInstance().getUser());
                    break;
                case "2":
                    String query = clientService.formingQuerySearchMyActionLogs();
                    clientService.searchMyActionLog(query, Session.getInstance().getUser());
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
            System.out.println("3\uFE0F⃣ View my cars");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    clientService.viewAllCars();
                    break;

                case "2":
                    String query = clientService.formingQuerySearchCars();
                    clientService.searchCars(query);
                    break;

                case "3":
                    clientService.viewMyCars(Session.getInstance().getUser());
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
            System.out.println("1\uFE0F⃣ View my requests");
            System.out.println("2\uFE0F⃣ Create a new request");
            System.out.println("3\uFE0F⃣ Cancel an request");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    clientService.viewMyRequests(Session.getInstance().getUser());
                    break;

                case "2":
                    addRequestConsole();
                    break;

                case "3":
                    cancelRequest();
                    break;
                case "back":
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

    private void addRequestConsole() {

        System.out.println("\uD83C\uDD95 Create a new request:");
        Car car = selectFromMyCars();
        while (car == null) {
            ErrorResponses.printCustomMessage("Car selection failed.");
            System.out.println("\uD83C\uDD95 Create a new order:");
            car = selectFromMyCars();
        }

        System.out.println("Enter a note for the order:");
        String note = scanner.nextLine();

        Order order = new Order(car, Session.getInstance().getUser(), Order.TypeOrder.SERVICE, note);

        clientService.addOrder(order);

        SuccessResponses.printCustomMessage("Order created successfully: " + order);
    }

    private Car selectFromMyCars() {
        System.out.println("Available cars:");
        clientService.viewMyCars(Session.getInstance().getUser());
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

    private void cancelRequest(){
        System.out.println("Input please, id of request you want to cancel: ");
        String inputCancelId = scanner.nextLine().trim();
        Order orderCancel = clientService.findOrderById(UUID.fromString(inputCancelId));
        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want cancel this:");
        System.out.println(orderCancel + ConsoleColors.RESET);
        if (!confirm("Request canceled.")) return;
        clientService.cancelOrder(orderCancel);
    }

    public void handleOrders() {
        while (true) {
            System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Orders Menu ===" + ConsoleColors.RESET);
            System.out.println("1\uFE0F⃣ View my orders");
            System.out.println("2\uFE0F⃣ Create a new order");
            System.out.println("3\uFE0F⃣ Cancel an order");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    clientService.viewMyOrders(Session.getInstance().getUser());
                    break;

                case "2":
                    addOrderConsole();
                    break;

                case "3":
                    cancelOrder();
                    break;
                case "back":
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

    private void addOrderConsole() {

        System.out.println("\uD83C\uDD95 Create a new order:");
        Car car = selectCar();
        while (car == null) {
            ErrorResponses.printCustomMessage("Car selection failed.");
            System.out.println("\uD83C\uDD95 Create a new order:");
            car = selectCar();
        }

        System.out.println("Enter a note for the order:");
        String note = scanner.nextLine();

        Order order = new Order(car, Session.getInstance().getUser(), Order.TypeOrder.BUYING, note);

        clientService.addOrder(order);

        SuccessResponses.printCustomMessage("Order created successfully: " + order);
    }

    private void cancelOrder() {
        System.out.println("Input please, id of order you want to cancel: ");
        String inputCancelId = scanner.nextLine().trim();
        Order orderCancel = clientService.findOrderById(UUID.fromString(inputCancelId));
        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want cancel this:");
        System.out.println(orderCancel + ConsoleColors.RESET);
        if (!confirm("Request canceled.")) return;
        clientService.cancelOrder(orderCancel);
    }
}
