package com.backend.view;

import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.view.handler.*;

import java.util.Scanner;

public class MenuHolderClient{

    Scanner scanner = new Scanner(System.in);

    CarMenuHandler carMenuHandler = new CarMenuHandler();
    RequestMenuHandler requestMenuHandler = new RequestMenuHandler();
    OrderMenuHandler orderMenuHandler = new OrderMenuHandler();
    ActionLogMenuHandler actionLogMenuHandler = new ActionLogMenuHandler();

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
                    carMenuHandler.viewAll();
                    break;

                case "2":
                    carMenuHandler.search();
                    break;

                case "3":
                    carMenuHandler.viewByClient();
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
                    requestMenuHandler.viewByClient();
                    break;

                case "2":
                    requestMenuHandler.createByUser();
                    break;

                case "3":
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
            System.out.println("1\uFE0F⃣ View my orders");
            System.out.println("2\uFE0F⃣ Create a new order");
            System.out.println("3\uFE0F⃣ Cancel an order");
            System.out.println("\uD83D\uDD19 Type 'back' to return to the main menu.");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    orderMenuHandler.getByClient();
                    break;

                case "2":
                    orderMenuHandler.createByClient();
                    break;

                case "3":
                    orderMenuHandler.cancel();
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
