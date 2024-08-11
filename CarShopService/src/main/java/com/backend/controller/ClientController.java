package com.backend.controller;

import com.backend.model.ActionLog;
import com.backend.service.UserAbstractService;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.view.MenuHolderClient;

import java.util.Scanner;

public class ClientController {

    private static final Scanner scanner = new Scanner(System.in);

    private MenuHolderClient menuHolderClient;

    public ClientController (){
        menuHolderClient = new MenuHolderClient();
    }

    public void start () {
        System.out.println(ConsoleColors.BLUE_BOLD + "\nWelcome my dear friend!");
        System.out.println("Client: " + Session.getInstance().getUser().getUsername() + ConsoleColors.RESET);
        while (true) {
            menuHolderClient.showMainMenu();
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
                case "end":
                    Session.getInstance().setStage(Session.Stage.EXIT);
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

}
