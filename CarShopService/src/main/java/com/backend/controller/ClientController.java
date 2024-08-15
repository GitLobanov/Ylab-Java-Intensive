package com.backend.controller;

import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.view.MenuHolderClient;

import java.util.Scanner;

public class ClientController implements Controller  {

    private static final Scanner scanner = new Scanner(System.in);

    private MenuHolderClient menuHolderClient;

    public ClientController (){
        menuHolderClient = new MenuHolderClient();
    }

    @Override
    public void showMenu() {
        System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Main Menu ===" + ConsoleColors.RESET);
        System.out.println("\uD83D\uDE97 Type 'c/cars' to cars menu.");
        System.out.println("\uD83D\uDE97 Type 'r/requests' to requests menu.");
        System.out.println("\uD83D\uDCE6 Type 'o/orders' to orders menu.");
        System.out.println("\uD83D\uDDC3\uFE0F Type 'a/actions' to manage actions.");
        System.out.println("\uD83D\uDD10 Type 'l/logout' to logout from account.");
        System.out.println("\uD83D\uDD1A Type 'exit' to quit the application.");
        System.out.print("Enter your choice: ");
    }

    public void start () {
        System.out.println(ConsoleColors.BLUE_BOLD + "\nWelcome my dear friend!");
        System.out.println("Client: " + Session.getInstance().getUser().getUsername() + ConsoleColors.RESET);
        while (true) {
            showMenu();
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "c":
                case "cars":
                    menuHolderClient.handleCars();
                    break;
                case "r":
                case "requests":
                    menuHolderClient.handleServiceRequests();
                    break;
                case "o":
                case "orders":
                    menuHolderClient.handleOrders();
                    break;

                case "a":
                case "actions":
                    menuHolderClient.handleLogging();
                    break;
                case "l":
                case "logout":
                    System.out.println("Exiting from account...");
                    Session.getInstance().setStage(Session.Stage.HAVE_TO_LOGIN);
                    return;
                case "exit":
                    Session.getInstance().setStage(Session.Stage.EXIT);
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

}
