package com.backend.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ErrorResponses {

    public static final List<String> RESPONSES_TO_UNKNOWN_COMMAND;
    static {
        RESPONSES_TO_UNKNOWN_COMMAND = new ArrayList<>();
        RESPONSES_TO_UNKNOWN_COMMAND.add("Oops! Looks like you’ve just discovered a secret command... well, a secret to everyone including me!");
        RESPONSES_TO_UNKNOWN_COMMAND.add("Hmm, that command seems to be from an alternate universe where it actually works. Can you try something from this galaxy?");
        RESPONSES_TO_UNKNOWN_COMMAND.add("I don’t recognize that command. Maybe it’s in code for ‘I’m still learning’?");
        RESPONSES_TO_UNKNOWN_COMMAND.add("Unknown command detected. Please check if you’re typing in Klingon or just using an old-timey typewriter.");
        RESPONSES_TO_UNKNOWN_COMMAND.add("Error: Your command was so mysterious, it’s now part of the Bermuda Triangle of code!");
        RESPONSES_TO_UNKNOWN_COMMAND.add("That command is so unknown, it’s been banned from our system for excessive shyness.");
        RESPONSES_TO_UNKNOWN_COMMAND.add("Error 404: Command not found. I think it went on vacation without telling us.");
        RESPONSES_TO_UNKNOWN_COMMAND.add("Looks like you’re trying to summon something that only exists in the land of unicorns. Try a different command!");
        RESPONSES_TO_UNKNOWN_COMMAND.add("Uh-oh! I think that command is playing hide and seek. Try a more popular one!");
        RESPONSES_TO_UNKNOWN_COMMAND.add("Your command has been sent to the Twilight Zone for further investigation. I’ll let you know if it returns!");
    }

    public static final List<String> RESPONSES_TO_USERNAME_ALREADY_EXIST;

    static {
        RESPONSES_TO_USERNAME_ALREADY_EXIST = new ArrayList<>();
        RESPONSES_TO_USERNAME_ALREADY_EXIST.add("Whoa! This username already belongs to someone else. It’s like finding out you’re sharing your name with a celebrity!");
        RESPONSES_TO_USERNAME_ALREADY_EXIST.add("Looks like you’ve picked a username that's already taken. Maybe it's time for a unique name like ‘SuperUser’?");
        RESPONSES_TO_USERNAME_ALREADY_EXIST.add("Error: The username you chose is currently in use. Looks like it’s got a fan club!");
        RESPONSES_TO_USERNAME_ALREADY_EXIST.add("Sorry, but this username is already reserved. Perhaps you can go for something as unique as ‘MasterOfTheUniverse’?");
        RESPONSES_TO_USERNAME_ALREADY_EXIST.add("Oops! This username is already in the system. It must be really popular. Try adding a cool suffix like ‘_TheRealDeal’!");
    }

    public static final List<String> RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT;

    static {
        RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT = new ArrayList<>();
        RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT.add("Oops! It looks like your username or password has gone on vacation. Double-check and try again!");
        RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT.add("Whoa there! Your username or password seems to be on a secret mission. Can you try again?");
        RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT.add("Looks like you’ve entered the wrong username or password. Maybe you were aiming for a top-secret code?");
        RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT.add("Uh-oh! Your login credentials seem to be playing hide and seek. Check and try one more time!");
        RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT.add("Error: The username or password you entered is incorrect. Maybe you were thinking of a different universe’s credentials?");
    }

    public static void printRandom (List list) {
        System.out.println("⚠\uFE0F "+list.get(new Random().nextInt(list.size()-1)));
    }

}
