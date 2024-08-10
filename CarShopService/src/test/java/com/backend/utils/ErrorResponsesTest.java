package com.backend.utils;

import com.backend.util.ErrorResponses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ErrorResponsesTest {

    private static ErrorResponses errorResponses;

    @BeforeAll
    public static void setUp() {
        errorResponses = new ErrorResponses();
    }

    @Test
    public void testResponsesToUnknownCommand() {
        List<String> responses = ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND;
        assertNotNull(responses, "RESPONSES_TO_UNKNOWN_COMMAND should not be null");
        assertFalse(responses.isEmpty(), "RESPONSES_TO_UNKNOWN_COMMAND should not be empty");
        assertEquals(10, responses.size(), "RESPONSES_TO_UNKNOWN_COMMAND should contain 10 elements");
    }

    @Test
    public void testResponsesToUsernameAlreadyExist() {
        List<String> responses = ErrorResponses.RESPONSES_TO_USERNAME_ALREADY_EXIST;
        assertNotNull(responses, "RESPONSES_TO_USERNAME_ALREADY_EXIST should not be null");
        assertFalse(responses.isEmpty(), "RESPONSES_TO_USERNAME_ALREADY_EXIST should not be empty");
        assertEquals(5, responses.size(), "RESPONSES_TO_USERNAME_ALREADY_EXIST should contain 5 elements");
    }

    @Test
    public void testResponsesToUsernameOrPasswordIncorrect() {
        List<String> responses = ErrorResponses.RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT;
        assertNotNull(responses, "RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT should not be null");
        assertFalse(responses.isEmpty(), "RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT should not be empty");
        assertEquals(5, responses.size(), "RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT should contain 5 elements");
    }

    @Test
    public void testPrintRandom() {
        assertDoesNotThrow(() -> {
            ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
            ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_USERNAME_ALREADY_EXIST);
            ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_USERNAME_OR_PASSWORD_INCORRECT);
        }, "printRandom should not throw any exceptions");
    }

    @Test
    public void testPrintCustomMessage() {
        String message = "This is a test message.";
        assertDoesNotThrow(() -> ErrorResponses.printCustomMessage(message), "printCustomMessage should not throw any exceptions");
    }
}