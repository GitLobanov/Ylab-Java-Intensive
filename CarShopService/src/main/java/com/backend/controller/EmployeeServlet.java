package com.backend.controller;

import com.backend.dto.EmployeeDTO;
import com.backend.mapper.EmployeeMapper;
import com.backend.model.User;
import com.backend.service.EmployeeService;
import com.backend.util.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "EmployeeServlet", urlPatterns = "/api/employee/*")
public class EmployeeServlet extends HttpServlet {

    private final ObjectMapper objectMapper;
    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;
    private EmployeeService employeeService;
    public EmployeeServlet() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        employeeService = new EmployeeService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = ServletUtils.getAction(req);

        switch (action) {
            case "employee":
                handleGetEmployees(req, resp);
                break;
            case "filter":
                handleFilterEmployee(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = employeeMapper.toEntity(getDTO(req));
        employeeService.addEmployee(user);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> optionalClient = employeeService.getByUsername(getDTO(req).getUsername());

        if (optionalClient.isPresent()) {
            employeeService.removeEmployee(optionalClient.get().getUsername());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found");
            return;
        }

        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeeDTO employeeDTO = getDTO(req);
        Optional<User> userUpdate = employeeService.getByUsername(employeeDTO.getUsername());

        if (userUpdate.isPresent()) {
            employeeMapper.updateFromDto(employeeDTO, userUpdate.get());
            employeeService.updateEmployee(userUpdate.get());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found");
            return;
        }

        doGet(req, resp);
    }

    private EmployeeDTO getDTO(HttpServletRequest req) throws IOException {
        EmployeeDTO employeeDTO = objectMapper.readValue(req.getInputStream(), EmployeeDTO.class);
        return employeeDTO;
    }

    private void handleGetEmployees(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] bytes = objectMapper.writeValueAsBytes(employeeService.getAllEmployees());
        resp.getOutputStream().write(bytes);
    }

    private void handleFilterEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeeDTO employeeDTO = objectMapper.readValue(req.getInputStream(), EmployeeDTO.class);
        User user = employeeMapper.toEntity(employeeDTO);
        byte[] bytes = objectMapper.writeValueAsBytes(employeeService.getEmployeesBySearch(user));
        resp.getOutputStream().write(bytes);
    }

}
