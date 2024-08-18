package com.backend.controller;

import com.backend.dto.ClientDTO;
import com.backend.dto.OrderDTO;
import com.backend.mapper.ClientMapper;
import com.backend.mapper.OrderMapper;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.service.OrderService;
import com.backend.service.impl.ClientService;
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

@WebServlet(name = "RequestServiceServlet", urlPatterns = "/api/requests/*")
public class RequestServiceServlet extends HttpServlet {

    private final ObjectMapper objectMapper;
    private OrderMapper orderMapper = OrderMapper.INSTANCE;
    private ClientService clientService;
    private OrderService orderService;
    public RequestServiceServlet() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        clientService = new ClientService();
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = ServletUtils.getAction(req);

        switch (action) {
            case "requests":
                handleGetRequests(req, resp);
                break;
            case "client":
                handleGetClientRequests(req, resp);
                break;
            case "filter":
                handleFilterRequests(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = orderMapper.toEntity(getDTO(req));
        if (orderService.addOrder(order)) resp.setStatus(HttpServletResponse.SC_CREATED);
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Order> optionalOrder = orderService.getOrderById(getDTO(req).getId());

        if (optionalOrder.isPresent()) {
            orderService.deleteOrder(optionalOrder.get());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
            return;
        }

        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDTO orderDTO = getDTO(req);
        Optional<Order> orderUpdate = orderService.getOrderById(orderDTO.getId());

        if (orderUpdate.isPresent()) {
            orderMapper.updateFromDto(orderDTO, orderUpdate.get());
            orderService.updateOrder(orderUpdate.get());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
            return;
        }

        doGet(req, resp);
    }

    private OrderDTO getDTO(HttpServletRequest req) throws IOException {
        OrderDTO orderDTO = objectMapper.readValue(req.getInputStream(), OrderDTO.class);
        return orderDTO;
    }

    private void handleGetRequests(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] bytes = objectMapper.writeValueAsBytes(orderService.getAllServiceOrders());
        resp.getOutputStream().write(bytes);
    }

    private void handleGetClientRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ClientDTO clientDTO = objectMapper.readValue(req.getInputStream(), ClientDTO.class);
        byte[] bytes = objectMapper.writeValueAsBytes(orderService.getClientRequests(clientDTO.getUsername()));
        resp.getOutputStream().write(bytes);
    }

    private void handleFilterRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrderDTO orderDTO = objectMapper.readValue(req.getInputStream(), OrderDTO.class);
        byte[] bytes = objectMapper.writeValueAsBytes(orderService.getOrdersBySearch(orderDTO));
        resp.getOutputStream().write(bytes);
    }
}
