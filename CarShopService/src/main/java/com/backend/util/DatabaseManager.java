package com.backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {

    private static final String PATH_TO_DB_PROPERTIES = "properties/db.properties";
    private static String jdbcUrl;
    private static String username;
    private static String password;
    private static String driverClassName;

    public DatabaseManager() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PATH_TO_DB_PROPERTIES)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + PATH_TO_DB_PROPERTIES);
            }
            Properties properties = new Properties();
            properties.load(input);
            jdbcUrl = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            driverClassName = properties.getProperty("driver");

            // Register the JDBC driver
            Class.forName(driverClassName);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
