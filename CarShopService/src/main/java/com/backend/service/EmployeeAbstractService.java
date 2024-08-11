package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

public abstract class EmployeeAbstractService extends UserAbstractService {

    public boolean addCar(Car car) {
        log(ActionLog.ActionType.CREATE, "Created car");
        return carRepository.save(car);
    }


    public boolean updateCar(Car car) {
        log(ActionLog.ActionType.CREATE, "Updated car");
        return carRepository.update(car);
    }

    public boolean deleteCar(Car car) {
        log(ActionLog.ActionType.DELETE, "Deleted car");
        return carRepository.delete(car);
    }


    public Car findCarById(int id) {
        Car car = carRepository.findById(id);
        return car;
    }


    public void viewAllBuyingOrders (){
        log(ActionLog.ActionType.VIEW, "View buying orders");
        Iterator<Order> iterator = orderRepository.findByType(Order.TypeOrder.BUYING).iterator();
        displaySearchResultOrder(iterator);
    }


    public void viewAllServiceOrders() {
        log(ActionLog.ActionType.VIEW, "View service orders");
        Iterator<Order> iterator = orderRepository.findByType(Order.TypeOrder.SERVICE).iterator();

        if (!iterator.hasNext()) {
            ErrorResponses.printCustomMessage("Hmm, sorry I cannot find any requests");
        }

        displaySearchResultOrder(iterator);
    }



    public void searchOrders(String query) {
        log(ActionLog.ActionType.VIEW, "Search orders");

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

        Iterator<Order> iterator = result.iterator();

        displaySearchResultOrder(iterator);

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



    public void viewAllClients () {
        log(ActionLog.ActionType.VIEW, "View all clients");

        Iterator<User> iterator = userRepository.findAll().iterator();
        while (iterator.hasNext()) {
            System.out.println(ConsoleColors.PURPLE_BOLD + iterator.next() + ConsoleColors.RESET);
        }
    }


    public boolean addClient(User client) {
        return userRepository.save(client);
    }


    public boolean updateClient(String userName, User updatedClient) {
        log(ActionLog.ActionType.UPDATE, "Update order");

        if (userRepository.findByUserName(userName)==null) {
            ErrorResponses.printCustomMessage("Client not found");
            return false;
        } else {
            userRepository.update(updatedClient);
            return true;
        }
    }


    public boolean removeClient(String userName) {
        log(ActionLog.ActionType.DELETE, "Delete client");

        if (userRepository.findByUserName(userName)==null){
            ErrorResponses.printCustomMessage("Client not found.");
            return false;
        }
        User employee = userRepository.findByUserName(userName);
        if (employee.getRole() == User.Role.CLIENT) {
            userRepository.delete(employee);
            return true;
        } else {
            ErrorResponses.printCustomMessage("Hey! Who you want to delete? This user is not a client!");
            System.out.println(ConsoleColors.YELLOW_BOLD + "User role must be ADMIN or MANAGER." + ConsoleColors.RESET);
            return false;
        }
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


    public void searchClients(String query) {
        log(ActionLog.ActionType.VIEW, "Search clients");

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
                    predicates.add(user -> getClientOrderCount(user) >= orderCount);
                    break;
                case "amountOfServiceRequests":
                    int serviceCount = Integer.parseInt(value);
                    predicates.add(user -> getClientServiceRequestCount(user) >= serviceCount);
                    break;
            }
        });

        Iterator<User> iterator = userRepository.findAll().stream()
                .filter(entry -> entry.getRole() == User.Role.CLIENT)
                .filter(entry -> predicates.stream().reduce(x -> true, Predicate::and).test(entry))
                .iterator();

        displaySearchResultUser(iterator);
    }

}
