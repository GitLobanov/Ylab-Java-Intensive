package com.backend.controller;

import com.backend.model.ActionLog;
import com.backend.service.parent.UserAbstractService;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.view.MenuHolderManager;

import java.util.Scanner;

public class ManagerController {

    private static final Scanner scanner = new Scanner(System.in);

    private MenuHolderManager menuHolderManager;

    public ManagerController (){
        menuHolderManager = new MenuHolderManager();
    }

    public void start () {
        System.out.println("Hello, " + Session.getInstance().getUser().getName() + ConsoleColors.RESET);
        while (true) {
            menuHolderManager.showMainMenu();
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "c":
                case "cars":
                    menuHolderManager.handleCars();
                    break;
                case "r":
                case "requests":
                    menuHolderManager.handleServiceRequests();
                    break;
                case "o":
                case "orders":
                    menuHolderManager.handleOrders();
                    break;
                case "cl":
                case "clients":
                    menuHolderManager.handleClients();
                    break;

                case "a":
                case "actions":
                    menuHolderManager.handleLogging();
                    break;
                case "l":
                case "logout":
                    System.out.println("Exiting from account...");
                    Session.getInstance().setStage(Session.Stage.HAVE_TO_LOGIN);
                    UserAbstractService.log(ActionLog.ActionType.LOGOUT, "");
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
