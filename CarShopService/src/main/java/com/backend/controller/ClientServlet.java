package com.backend.controller;

import com.backend.dto.CarDTO;
import com.backend.dto.ClientDTO;
import com.backend.mapper.CarMapper;
import com.backend.mapper.ClientMapper;
import com.backend.model.Car;
import com.backend.model.User;
import com.backend.service.CarService;
import com.backend.service.impl.ClientService;
import com.backend.util.ServletUtils;
import com.backend.util.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ClientServlet", urlPatterns = "/api/clients/*")
public class ClientServlet extends HttpServlet {

    private final ObjectMapper objectMapper;
    private ClientMapper clientMapper = ClientMapper.INSTANCE;
    private ClientService clientService;
    public ClientServlet() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        clientService = new ClientService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = ServletUtils.getAction(req);

        switch (action) {
            case "cars":
                handleGetClientCars(req, resp);
                break;
            case "clients":
                handleGetClients(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = clientMapper.toEntity(getDTO(req));
        clientService.addClient(user);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> optionalClient = clientService.getClientByUsername(getDTO(req).getUsername());

        if (optionalClient.isPresent()) {
            clientService.removeClient(optionalClient.get().getUsername());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
            return;
        }

        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientDTO clientDTO = getDTO(req);
        Optional<User> optionalClient = clientService.getClientByUsername(clientDTO.getUsername());

        if (optionalClient.isPresent()) {
            User clientUpdate = optionalClient.get();
            clientMapper.updateFromDto(clientDTO, clientUpdate);
            clientService.updateClient(optionalClient.get().getUsername(), clientUpdate);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
        }

        doGet(req, resp);
    }

    private ClientDTO getDTO(HttpServletRequest req) throws IOException {
        ClientDTO clientDTO = objectMapper.readValue(req.getInputStream(), ClientDTO.class);
        return clientDTO;
    }

    private void handleGetClientCars(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientDTO clientDTO = objectMapper.readValue(req.getInputStream(), ClientDTO.class);
        byte[] bytes = objectMapper.writeValueAsBytes(clientService.getClientCars(clientDTO.getUsername()));
        resp.getOutputStream().write(bytes);
    }

    private void handleGetClients(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] bytes = objectMapper.writeValueAsBytes(clientService.getAllClients());
        resp.getOutputStream().write(bytes);
    }

}
