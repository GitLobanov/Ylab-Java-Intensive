package com.backend.controller;

import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.view.MenuHolderAdmin;

import java.util.Scanner;

public class AdminController implements Controller {

    private static final Scanner scanner = new Scanner(System.in);

    private MenuHolderAdmin menuHolderAdmin;

    public AdminController (){
        menuHolderAdmin = new MenuHolderAdmin();
    }

    @Override
    public void showMenu() {
        System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Main Menu ===" + ConsoleColors.RESET);
        System.out.println("\uD83D\uDE97 Type 'c/cars' to manage cars.");
        System.out.println("\uD83D\uDE97 Type 'r/requests' to manage cars.");
        System.out.println("\uD83D\uDCE6 Type 'o/orders' to manage orders.");
        System.out.println("\uD83D\uDC68\uD83C\uDFFB\u200D⚖\uFE0F Type 'cl/clients' to manage clients.");
        System.out.println("\uD83D\uDC69\uD83C\uDFFB\u200D\uD83D\uDCBC Type 'e/employees' to manage employees.");
        System.out.println("\uD83D\uDDC3\uFE0F Type 'a/actions' to manage actions.");
        System.out.println("\uD83D\uDD10 Type 'l/logout' to logout from account.");
        System.out.println("\uD83D\uDD1A Type 'exit' to quit the application.");
        System.out.print("Enter your choice: ");
    }

    public void start () {
        System.out.println(ConsoleColors.BLUE_BOLD + "\n \uD83D\uDC51 Admen is HERE!");
        System.out.println("Hello, " + Session.getInstance().getUser().getName() + ConsoleColors.RESET);
        while (true) {
            showMenu();
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "c":
                case "cars":
                    menuHolderAdmin.handleCars();
                    break;
                case "r":
                case "requests":
                    menuHolderAdmin.handleServiceRequests();
                    break;
                case "o":
                case "orders":
                    menuHolderAdmin.handleOrders();
                    break;
                case "cl":
                case "clients":
                    menuHolderAdmin.handleClients();
                    break;
                case "e":
                case "employees":
                   menuHolderAdmin.handleEmployees();
                    break;

                case "a":
                case "actions":
                    menuHolderAdmin.handleLogging();
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
