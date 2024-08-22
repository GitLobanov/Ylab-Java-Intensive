package com.backend.controller;

import com.backend.dto.EmployeeDTO;
import com.backend.model.User;
import com.backend.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeServletTest {

    private Server server;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder servletHolder = new ServletHolder(new EmployeeServlet());
        context.addServlet(servletHolder, "/api/employee/*");

        server.start();

        objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() throws Exception {
        server.stop();
        server.join();
    }

    @Test
    void testDoGetEmployees() throws IOException {
        // Вставьте тестовые данные в EmployeeService
        // Примерно: employeeService.addEmployee(new User("testuser", "Test Employee"));

        URL url = new URL("http://localhost:8081/api/employee");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        ByteArrayOutputStream responseOutput = new ByteArrayOutputStream();
        try (var inputStream = connection.getInputStream()) {
            inputStream.transferTo(responseOutput);
        }

        String response = responseOutput.toString(StandardCharsets.UTF_8);
        // Ожидаемое значение - замените на ожидаемое значение
        String expected = objectMapper.writeValueAsString(Collections.singletonList(new User()));

        assertEquals(expected, response);
        assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
    }

    @Test
    void testDoPost() throws IOException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        // Настройте employeeDTO, если необходимо

        URL url = new URL("http://localhost:8081/api/employee");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        try (PrintWriter writer = new PrintWriter(connection.getOutputStream())) {
            writer.write(objectMapper.writeValueAsString(employeeDTO));
            writer.flush();
        }

        assertEquals(HttpURLConnection.HTTP_CREATED, connection.getResponseCode());
    }

    @Test
    void testDoDelete() throws IOException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        // Настройте employeeDTO, если необходимо

        URL url = new URL("http://localhost:8081/api/employee");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        try (PrintWriter writer = new PrintWriter(connection.getOutputStream())) {
            writer.write(objectMapper.writeValueAsString(employeeDTO));
            writer.flush();
        }

        assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
    }

    @Test
    void testDoPut() throws IOException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        // Настройте employeeDTO, если необходимо

        URL url = new URL("http://localhost:8081/api/employee");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        try (PrintWriter writer = new PrintWriter(connection.getOutputStream())) {
            writer.write(objectMapper.writeValueAsString(employeeDTO));
            writer.flush();
        }

        assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
    }
}
