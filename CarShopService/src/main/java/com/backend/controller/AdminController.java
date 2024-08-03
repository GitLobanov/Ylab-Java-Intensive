package com.backend.controller;

import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.view.MenuHolderAdmin;

import java.util.Scanner;

public class AdminController {

    private static final Scanner scanner = new Scanner(System.in);

    private MenuHolderAdmin menuHolderAdmin;

    public AdminController (){
        menuHolderAdmin = new MenuHolderAdmin();
    }

    public void start () {
        System.out.println(ConsoleColors.BLUE_BOLD + "\n \uD83D\uDC51 Admen is HERE!");
        System.out.println("Hello, " + Session.getInstance().getUser().getName() + ConsoleColors.RESET);
        while (true) {
            menuHolderAdmin.showMainMenu();
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
                case "l":
                case "logout":
                    System.out.println("Exiting from account...");
                    Session.getInstance().setStage(Session.Stage.HAVE_TO_LOGIN);
                    return;
                case "end":
                    Session.getInstance().setStage(Session.Stage.EXIT);
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

}
