package com.backend.service.user.parent;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.CarRepository;
import com.backend.repository.OrderRepository;
import com.backend.repository.UserRepository;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;

import java.time.LocalDateTime;
import java.util.*;

public abstract class EmployeeAbstractService extends UserAbstractService {

    public boolean addCar(Car car) {
        return CarRepository.getInstance().save(car);
    }
    public boolean updateCar(Car car) {
        return CarRepository.getInstance().update(car);
    }
    public boolean deleteCar(Car car) {
        return CarRepository.getInstance().delete(car);
    }

    public Car findCarById(UUID id) {
        Car car = CarRepository.getInstance().findById(id);
        return car;
    }

    public void viewAllBuyingOrders (){
        Iterator<Map.Entry<UUID, Order>> iterator = OrderRepository.getInstance().findByType(Order.TypeOrder.BUYING).entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(ConsoleColors.PURPLE_BOLD + iterator.next().getValue() + ConsoleColors.RESET);
        }
    }

    public void viewAllServiceOrders() {
        Iterator<Map.Entry<UUID, Order>> iterator = OrderRepository.getInstance().findByType(Order.TypeOrder.SERVICE).entrySet().iterator();

        if (!iterator.hasNext()) {
            ErrorResponses.printCustomMessage("Hmm, sorry I cannot find any requests");
        }

        while (iterator.hasNext()) {
            System.out.println(ConsoleColors.PURPLE_BOLD + iterator.next().getValue() + ConsoleColors.RESET);
        }
    }

    public void searchOrders(String query) {
        Map<UUID, Order> result = new HashMap<>();

        String[] filters = query.split(";");
        Map<String, String> filterMap = new HashMap<>();

        for (String filter : filters) {
            if (filter.contains(":")) {
                String[] keyValue = filter.split(":");
                filterMap.put(keyValue[0], keyValue[1]);
            }
        }

        for (Map.Entry<UUID, Order> entry : OrderRepository.getInstance().findByType(Order.TypeOrder.BUYING).entrySet()) {
            Order order = entry.getValue();
            boolean matches = true;

            if (filterMap.containsKey("brand") && !order.getCar().getBrand().equalsIgnoreCase(filterMap.get("brand"))) {
                matches = false;
            }

            if (filterMap.containsKey("client") && !order.getClient().getUserName().equalsIgnoreCase(filterMap.get("client"))) {
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

            if (filterMap.containsKey("dateFrom") && order.getOrderDateTime().isBefore(LocalDateTime.parse(filterMap.get("dateFrom")))) {
                matches = false;
            }

            if (filterMap.containsKey("dateTo") && order.getOrderDateTime().isAfter(LocalDateTime.parse(filterMap.get("dateTo")))) {
                matches = false;
            }

            if (matches) {
                result.put(entry.getKey(), order);
            }
        }

        Iterator<Map.Entry<UUID, Order>> iterator = result.entrySet().iterator();

        if (!iterator.hasNext()) {
            ErrorResponses.printCustomMessage("What did you choose? I can't find anything at all");
        }

        if (iterator.hasNext()) {
            System.out.println("Fond: ");
        }

        while (iterator.hasNext()) {
            System.out.println(iterator.next().getValue());
        }
    }

    public String formingQuerySearchOrders (Order.TypeOrder typeOrder){
        Scanner scanner = new Scanner(System.in);
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

    public boolean updateOrderStatus(Order order, Order.OrderStatus status) {
        order.setStatus(status);
        return OrderRepository.getInstance().update(order);
    }

    public boolean cancelOrder(Order order) {
        order.setStatus(Order.OrderStatus.CANCELLED);
        return OrderRepository.getInstance().update(order);
    }

    // Просмотр информации о клиентах и сотрудниках
    public void viewAllClients () {
        Iterator<Map.Entry<UUID, User>> iterator = UserRepository.getInstance().findAll().entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(ConsoleColors.PURPLE_BOLD + iterator.next().getValue() + ConsoleColors.RESET);
        }
    }

}
