package com.backend.service.impl;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.OrderRepository;
import com.backend.repository.impl.UserRepository;
import com.backend.service.ActionLogService;
import com.backend.service.OrderService;

import java.util.*;
import java.util.function.Predicate;

public class ClientService {

    Scanner scanner = new Scanner(System.in);

    ActionLogService actionLogService;
    OrderRepository orderRepository;
    CarRepository carRepository;
    UserRepository userRepository;

    OrderService orderService;

    public ClientService() {
        actionLogService = new ActionLogService();
        orderRepository = new OrderRepository();
        userRepository = new UserRepository();
        carRepository = new CarRepository();

        orderService = new OrderService();
    }

    public boolean addClient(User client) {
        client.setRole(User.Role.CLIENT);
        return userRepository.save(client);
    }


    public boolean updateClient(String userName, User updatedClient) {
        actionLogService.logAction(ActionLog.ActionType.UPDATE, "Update order");

        if (userRepository.findByUserName(userName)==null) {
            return false;
        } else {
            userRepository.update(updatedClient);
            return true;
        }
    }


    public boolean removeClient(String userName) {
        actionLogService.logAction(ActionLog.ActionType.DELETE, "Delete client");

        User client = userRepository.findByUserName(userName);
        return userRepository.delete(client);
    }


    public List<Car> getClientCars(String username) {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "View own cars");
        return carRepository.findCarsByClient(username);
    }

    public Optional<User> getClientByUsername (String username){
        return userRepository.findByUserName(username) != null
                ? Optional.of(userRepository.findByUserName(username)) : Optional.empty();
    }

    public List<User> getAllClients () {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "View all clients");
        return userRepository.findUsersByRole(User.Role.CLIENT);
    }


    public String formingQuerySearchClients (){
        System.out.println("\nEnter search criteria for clients. Press Enter to skip a filter.");

        StringBuilder queryBuilder = new StringBuilder();

        System.out.print("Name (contains): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) queryBuilder.append("name:").append(name).append(";");

        System.out.print("Username (contains): ");
        String username = scanner.nextLine().trim();
        if (!username.isEmpty()) queryBuilder.append("username:").append(username).append(";");

        System.out.print("Phone (contains): ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) queryBuilder.append("phone:").append(phone).append(";");

        System.out.print("Email (contains): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) queryBuilder.append("email:").append(email).append(";");

        System.out.print("Amount of orders (from): ");
        String amountOfOrders = scanner.nextLine().trim();
        if (!amountOfOrders.isEmpty()) queryBuilder.append("amountOfOrders:").append(amountOfOrders).append(";");

        System.out.print("Amount of service requests (from): ");
        String amountOfServiceRequests = scanner.nextLine().trim();
        if (!amountOfServiceRequests.isEmpty())
            queryBuilder.append("amountOfServiceRequests:").append(amountOfServiceRequests).append(";");

        return queryBuilder.toString();
    }


    public List<User> getClientsBySearch(String query) {
        actionLogService.logAction(ActionLog.ActionType.VIEW, "Search clients");

        Map<String, String> filters = new HashMap<>();
        String[] criteria = query.split(";");
        for (String criterion : criteria) {
            String[] parts = criterion.split(":");
            if (parts.length == 2) {
                filters.put(parts[0].trim(), parts[1].trim());
            }
        }

        List<Predicate<User>> predicates = new ArrayList<>();

        filters.forEach((key, value) -> {
            switch (key) {
                case "name":
                    predicates.add(user -> user.getName().toLowerCase().contains(value.toLowerCase()));
                    break;
                case "username":
                    predicates.add(user -> user.getUsername().toLowerCase().contains(value.toLowerCase()));
                    break;
                case "phone":
                    predicates.add(user -> user.getPhone().toLowerCase().contains(value.toLowerCase()));
                    break;
                case "email":
                    predicates.add(user -> user.getEmail().toLowerCase().contains(value.toLowerCase()));
                    break;
                case "amountOfOrders":
                    int orderCount = Integer.parseInt(value);
                    predicates.add(user -> orderService.getClientOrderCount(user) >= orderCount);
                    break;
                case "amountOfServiceRequests":
                    int serviceCount = Integer.parseInt(value);
                    predicates.add(user -> orderService.getClientServiceRequestCount(user) >= serviceCount);
                    break;
            }
        });

        return userRepository.findAll().stream()
                .filter(entry -> entry.getRole() == User.Role.CLIENT)
                .filter(entry -> predicates.stream().reduce(x -> true, Predicate::and).test(entry))
                .toList();

    }

}
