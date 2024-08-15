package com.backend.view;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.SuccessResponses;
import com.backend.view.handler.*;

import java.util.Scanner;

public class MenuHolderManager {

    Scanner scanner = new Scanner(System.in);

    CarMenuHandler carMenuHandler = new CarMenuHandler();
    RequestMenuHandler requestMenuHandler = new RequestMenuHandler();
    OrderMenuHandler orderMenuHandler = new OrderMenuHandler();
    ClientMenuHandler clientMenuHandler = new ClientMenuHandler();
    EmployeeMenuHandler employeeMenuHandler = new EmployeeMenuHandler();
    ActionLogMenuHandler actionLogMenuHandler = new ActionLogMenuHandler();


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
                    carMenuHandler.viewByClient();
                    break;

                case "2":
                    carMenuHandler.search();
                    break;

                case "3":
                    carMenuHandler.create();
                    break;

                case "4":
                    carMenuHandler.update();
                    break;

                case "5":
                    carMenuHandler.delete();
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
                    requestMenuHandler.viewAll();
                    break;

                case "2":
                    requestMenuHandler.search();
                    break;

                case "3":
                    requestMenuHandler.create();
                    break;

                case "4":
                    requestMenuHandler.viewByManager();
                    break;

                case "5":
                    requestMenuHandler.takeRequestForManager();
                    break;

                case "6":
                    requestMenuHandler.update();
                    break;

                case "7":
                    requestMenuHandler.cancel();
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
                    orderMenuHandler.viewAll();
                    break;

                case "2":
                    orderMenuHandler.search();
                    break;

                case "3":
                    orderMenuHandler.viewByManager();
                    break;

                case "4":
                    orderMenuHandler.create();
                    break;

                case "5":
                    orderMenuHandler.takeOrderForManager();
                    break;

                case "6":
                    orderMenuHandler.update();
                    break;

                case "7":
                    orderMenuHandler.cancel();
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
                    clientMenuHandler.viewAll();
                    break;
                case "2":
                    clientMenuHandler.search();
                    break;
                case "3":
                    clientMenuHandler.viewClientsByManager();
                    break;
                case "4":
                    clientMenuHandler.create();
                    break;
                case "5":
                    clientMenuHandler.update();
                    break;
                case "6":
                    clientMenuHandler.delete();
                    break;
                case "back":
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

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
                    actionLogMenuHandler.getByUser();
                    break;
                case "2":
                    actionLogMenuHandler.searchByUser();
                    break;
                case "back":
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }
}
