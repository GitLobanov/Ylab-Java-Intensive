package com.backend.controller;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.CarRepository;
import com.backend.repository.OrderRepository;
import com.backend.repository.UserRepository;
import com.backend.service.user.AdminService;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.util.menu.MenuHolder;
import com.backend.util.menu.MenuHolderAdmin;

import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class AdminController {

    private static final Scanner scanner = new Scanner(System.in);

    private MenuHolderAdmin menuHolderAdmin;

    public AdminController (){
        menuHolderAdmin = new MenuHolderAdmin();
    }

    public void start () {
        System.out.println("Admen is HERE!");
        System.out.println("Hello, " + Session.getInstance().getUser().getName() + " \uD83D\uDC51");
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
