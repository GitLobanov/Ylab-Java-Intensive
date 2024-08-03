package com.backend.controller;

import com.backend.model.User;
import com.backend.service.user.LoginRegisterService;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.util.SuccessResponses;

import java.util.Random;
import java.util.Scanner;

public class LoginRegisterController {

    private LoginRegisterService loginRegisterService = new LoginRegisterService();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(ConsoleColors.BLUE + "Authentication \uD83D\uDD11" + ConsoleColors.RESET);
            System.out.print("\uD83E\uDD14 What you want my friend? (register/login/end): ");
            String command = scanner.nextLine();
            switch (command.toLowerCase()) {
                case "register":
                    if (handleRegister(scanner)) return;
                    break;
                case "login":
                    if (handleLogin(scanner, Session.getInstance())) return;
                    break;
                case "end":
                    Session.getInstance().setStage(Session.Stage.EXIT);
                    return;
                default:
                    ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

    private boolean handleRegister(Scanner scanner) {
        System.out.println("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBC Input unique username:");
        String userName = scanner.nextLine();
        System.out.println("\uD83D\uDC4B What's your name (Second_name Name):");
        String name = scanner.nextLine();
        System.out.println("✉️ Yours email:");
        String email = scanner.nextLine();
        System.out.println("\uD83D\uDCF2 Yours number phone (7..):");
        String phone = scanner.nextLine();
        System.out.println("\uD83D\uDD12 Input password:");
        String password = scanner.nextLine();
        System.out.println("Input role (\uD83D\uDC51 ADMIN/\uD83D\uDC65 CLIENT/\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBC MANAGER):");
        String roleStr = scanner.nextLine();
        User.Role role = User.Role.valueOf(roleStr.toUpperCase());

        // Создание нового пользователя
        User user = new User(
                userName, password, role, name, email, phone
        );
        boolean success = loginRegisterService.register(user);
        if (success) {
            SuccessResponses.printCustomMessage("My congrats friend. Registration successfully.");
            return true;
        } else {
            String message = ErrorResponses.RESPONSES_TO_USERNAME_ALREADY_EXIST.get(new Random().nextInt(ErrorResponses.RESPONSES_TO_USERNAME_ALREADY_EXIST.size())-1);
            System.out.println(message);
            return false;
        }
    }

    private boolean handleLogin(Scanner scanner, Session session) {
//        System.out.println("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBC Please, yours username:");
//        String userName = scanner.nextLine();
//        System.out.println("\uD83D\uDD12 And password:");
//        String password = scanner.nextLine();

        String userName = "john_adman";
        String password = "123";

        User user = loginRegisterService.login(userName, password);
        if (user != null) {

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

            SuccessResponses.printCustomMessage("You have successfully logged in.");

            return true;
        } else {
            String message = ErrorResponses.RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT.get(new Random().nextInt(ErrorResponses.RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT.size())-1);
            System.out.println(message);
            return false;
        }
    }

}
