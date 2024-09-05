package com.backend.loggerstarter.config;


import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {

    private static final Logger timeLogger = Logger.getLogger("TimeLogger");

    public static Logger getAuditLogger() {
        return auditLogger;
    }

    private static final Logger auditLogger = Logger.getLogger("AuditLogger");

    public static Logger getTimeLogger() {
        return timeLogger;
    }

    static {
        try {
            FileHandler fileHandler = new FileHandler("execution-time.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            timeLogger.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            timeLogger.addHandler(consoleHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            FileHandler fileHandler = new FileHandler("audit.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            auditLogger.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            auditLogger.addHandler(consoleHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
