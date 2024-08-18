package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.OrderRepository;
import com.backend.util.ErrorResponses;

import java.time.LocalDate;
import java.util.*;

public class OrderService {

    Scanner scanner = new Scanner(System.in);

    ActionLogService actionLogService;
    OrderRepository orderRepository;
    CarRepository carRepository;

    public OrderService() {
        actionLogService =  new ActionLogService();
    }

    public boolean addOrder(Order order) {
        actionLogService.logAction(ActionLog.ActionType.CREATE, "Created order");
        checkCarForAvailability(order);
        return orderRepository.save(order);
    }


    public boolean updateOrder(Order order) {
        actionLogService.logAction(ActionLog.ActionType.UPDATE, "Updated order");
        checkCarForAvailability(order);
        return orderRepository.update(order);
    }


    public Order findOrderById(int id) {
        return orderRepository.findById(id);
    }

    private void checkCarForAvailability(Order order) {
        if (order.getType() == Order.TypeOrder.BUYING && order.getStatus() == Order.OrderStatus.COMPLETED) {
            Car car = order.getCar();
            car.setAvailability(false);
            carRepository.save(car);
        }
    }


    public boolean cancelOrder(Order order) {
        actionLogService.logAction(ActionLog.ActionType.CANCEL, "Canceled order");
        order.setStatus(Order.OrderStatus.CANCELLED);
        return orderRepository.update(order);
    }

    public int getClientOrderCount(User client) {
        return orderRepository.findOrdersByClient(client.getUsername()).size();
    }

    public int getClientServiceRequestCount(User client) {
        return orderRepository.findRequestsByClient(client.getUsername()).size();
    }

    public List<Order> getAllBuyingOrders (){
        actionLogService.logAction(ActionLog.ActionType.VIEW, "View buying orders");
        return orderRepository.findByType(Order.TypeOrder.BUYING);
    }

    public List<Order> getAllServiceOrders() {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "View service orders");
        return orderRepository.findByType(Order.TypeOrder.SERVICE);
    }

    public List<Order> getManagerOrders (User manager){
        actionLogService.logAction(ActionLog.ActionType.VIEW, "Manage orders");
        return orderRepository.findByManager(manager);
    }

    public List<Order> getOrdersBySearch(String query) {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "Search orders");

        List<Order> result = new ArrayList<>();

        String[] filters = query.split(";");
        Map<String, String> filterMap = new HashMap<>();

        for (String filter : filters) {
            if (filter.contains(":")) {
                String[] keyValue = filter.split(":");
                filterMap.put(keyValue[0], keyValue[1]);
            }
        }

        for (Order order : orderRepository.findByType(Order.TypeOrder.BUYING)) {
            boolean matches = true;

            if (filterMap.containsKey("brand") && !order.getCar().getBrand().equalsIgnoreCase(filterMap.get("brand"))) {
                matches = false;
            }

            if (filterMap.containsKey("client") && !order.getClient().getUsername().equalsIgnoreCase(filterMap.get("client"))) {
                matches = false;
            }

            if (filterMap.containsKey("status") && !order.getStatus().toString().equalsIgnoreCase(filterMap.get("status"))) {
                matches = false;
            }

            if (filterMap.containsKey("note") && !order.getNote().toLowerCase().contains(filterMap.get("note").toLowerCase())) {
                matches = false;
            }

            if (filterMap.containsKey("type") && !order.getType().toString().equalsIgnoreCase(filterMap.get("type"))) {
                matches = false;
            }

            if (filterMap.containsKey("yearFrom") && order.getCar().getYear() < Integer.parseInt(filterMap.get("yearFrom"))) {
                matches = false;
            }

            if (filterMap.containsKey("yearTo") && order.getCar().getYear() > Integer.parseInt(filterMap.get("yearTo"))) {
                matches = false;
            }

            if (filterMap.containsKey("dateFrom") && order.getOrderDate().toLocalDate().isBefore(LocalDate.parse(filterMap.get("dateFrom")))) {
                matches = false;
            }

            if (filterMap.containsKey("dateTo") && order.getOrderDate().toLocalDate().isAfter(LocalDate.parse(filterMap.get("dateTo")))) {
                matches = false;
            }

            if (matches) {
                result.add(order);
            }
        }

        return result;

    }

    public String formingQuerySearchOrders (Order.TypeOrder typeOrder){
        StringBuilder queryBuilder = new StringBuilder();

        System.out.println("Enter filter criteria for orders. Press Enter to skip a filter.");

        System.out.print("Car brand: ");
        String brand = scanner.nextLine().trim();
        if (!brand.isEmpty()) queryBuilder.append("brand:").append(brand).append(";");

        System.out.print("Client username: ");
        String clientUsername = scanner.nextLine().trim();
        if (!clientUsername.isEmpty()) queryBuilder.append("client:").append(clientUsername).append(";");

        System.out.print("Order status (PENDING, COMPLETED, CANCELLED): ");
        String status = scanner.nextLine().trim().toUpperCase();
        if (!status.isEmpty()) queryBuilder.append("status:").append(status).append(";");

        queryBuilder.append("type:").append(typeOrder).append(";");

        System.out.print("Note contains: ");
        String note = scanner.nextLine().trim();
        if (!note.isEmpty()) queryBuilder.append("note:").append(note).append(";");

        System.out.print("Year from: ");
        String yearFrom = scanner.nextLine().trim();
        if (!yearFrom.isEmpty()) queryBuilder.append("yearFrom:").append(yearFrom).append(";");

        System.out.print("Year to: ");
        String yearTo = scanner.nextLine().trim();
        if (!yearTo.isEmpty()) queryBuilder.append("yearTo:").append(yearTo).append(";");

        System.out.print("Date from (yyyy-MM-ddTHH:mm): ");
        String dateFrom = scanner.nextLine().trim();
        if (!dateFrom.isEmpty()) queryBuilder.append("dateFrom:").append(dateFrom).append(";");

        System.out.print("Date to (yyyy-MM-ddTHH:mm): ");
        String dateTo = scanner.nextLine().trim();
        if (!dateTo.isEmpty()) queryBuilder.append("dateTo:").append(dateTo).append(";");

        return queryBuilder.toString();
    }


}
