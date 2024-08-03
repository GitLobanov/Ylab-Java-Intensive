package com.backend.util.menu;

import com.backend.util.ErrorResponses;

import java.util.Scanner;

public abstract class MenuHolder {

    static final Scanner scanner = new Scanner(System.in);
    abstract void showMainMenu();
    abstract void handleCars();

    boolean confirm() {
        while (true) {
            System.out.println("Input yes/y or not/n");
            String answer = scanner.nextLine().trim();
            if (answer.equals("yes") || answer.equals("y")) {
                System.out.println("✅  Okay, as you wish my friend...");
                return true;
            } else if (answer.equals("no") || answer.equals("n")) {
                System.out.println("\uD83E\uDD1D Okay, keep this");
                return false;
            } else {
                ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            }
        }
    }

}
