package com.backend.config;

import lombok.Getter;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {

    @Getter
    private static final Logger logger = Logger.getLogger("AuditLogger");

    static {
        try {
            FileHandler fileHandler = new FileHandler("audit.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
