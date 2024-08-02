package com.backend.controller;

import com.backend.model.User;
import com.backend.service.user.LoginRegisterService;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;

import java.util.Random;
import java.util.Scanner;

public class LoginRegisterController {

    private LoginRegisterService loginRegisterService = new LoginRegisterService();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What you want my friend? (register/login/exit):");
            String command = scanner.nextLine();
            switch (command.toLowerCase()) {
                case "register":
                    if (handleRegister(scanner)) return;
                    break;
                case "login":
                    if (handleLogin(scanner, Session.getInstance())) return;
                    break;
                case "exit":
                    Session.getInstance().setStage(Session.Stage.EXIT);
                    return;
                default:
                    String message = ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND.get(new Random().nextInt(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND.size())-1);
                    System.out.println(message);
            }
        }
    }

    private boolean handleRegister(Scanner scanner) {
        System.out.println("Input unique username:");
        String userName = scanner.nextLine();
        System.out.println("What's your name (Second_name Name):");
        String name = scanner.nextLine();
        System.out.println("Yours email:");
        String email = scanner.nextLine();
        System.out.println("Yours number phone (7..):");
        String phone = scanner.nextLine();
        System.out.println("Input password:");
        String password = scanner.nextLine();
        System.out.println("Input role (ADMIN/USER/MANAGER):");
        String roleStr = scanner.nextLine();
        User.Role role = User.Role.valueOf(roleStr.toUpperCase());

        // Создание нового пользователя
        User user = new User(
                userName, password, role, name, email, phone
        );
        boolean success = loginRegisterService.register(user);
        if (success) {
            System.out.println("My congrats friend. Registration successfully.");
            return true;
        } else {
            String message = ErrorResponses.RESPONSES_TO_USERNAME_ALREADY_EXIST.get(new Random().nextInt(ErrorResponses.RESPONSES_TO_USERNAME_ALREADY_EXIST.size())-1);
            System.out.println(message);
            return false;
        }
    }

    private boolean handleLogin(Scanner scanner, Session session) {
        System.out.println("Please, yours username:");
        String userName = scanner.nextLine();
        System.out.println("And password:");
        String password = scanner.nextLine();

        User user = loginRegisterService.login(userName, password);
        if (user != null) {
            System.out.println("Authentication successful!");
            session.setUser(user);

            if (user.getRole() == User.Role.ADMIN) {
                session.setStage(Session.Stage.ADMIN);
            }

            if (user.getRole() == User.Role.MANAGER) {
                session.setStage(Session.Stage.MANAGER);
            }

            if (user.getRole() == User.Role.CLIENT) {
                session.setStage(Session.Stage.CLIENT);
            }

            return true;
        } else {
            String message = ErrorResponses.RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT.get(new Random().nextInt(ErrorResponses.RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT.size())-1);
            System.out.println(message);
            return false;
        }
    }

}
