package com.backend.view.handler;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.service.CarService;
import com.backend.service.OrderService;
import com.backend.service.impl.ClientService;
import com.backend.service.impl.ManagerService;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.util.SuccessResponses;

public class RequestMenuHandler extends BaseHandler{

    OrderService orderService = new OrderService();
    CarService carService = new CarService();
    ClientService clientService = new ClientService();
    ManagerService managerService = new ManagerService();

    @Override
    public void handlerMenu() {

    }

    @Override
    public void create() {
        System.out.println("\uD83C\uDD95 Create a new request:");
        Car car = selectCar();
        while (car == null) {
            ErrorResponses.printCustomMessage("Car selection failed.");
            System.out.println("\uD83C\uDD95 Create a new order:");
            car = selectCar();
        }

        User user = selectClient();
        while (user == null) {
            ErrorResponses.printCustomMessage("User selection failed.");
            user = selectClient();
        }

        System.out.println("Enter a note for the request:");
        String note = scanner.nextLine();

        Order order = new Order(0, car, user, Order.TypeOrder.SERVICE, note);

        orderService.addOrder(order);

        SuccessResponses.printCustomMessage("Order created successfully: " + order);
    }

    public void createByUser () {
        System.out.println("\uD83C\uDD95 Create a new request:");
        Car car = selectFromMyCars();
        while (car == null) {
            ErrorResponses.printCustomMessage("Car selection failed.");
            System.out.println("\uD83C\uDD95 Create a new order:");
            car = selectFromMyCars();
        }

        System.out.println("Enter a note for the order:");
        String note = scanner.nextLine();

        Order order = new Order(0, car, Session.getInstance().getUser(), Order.TypeOrder.SERVICE, note);

        orderService.addOrder(order);

        SuccessResponses.printCustomMessage("Order created successfully: " + order);
    }

    public Car selectFromMyCars() {
        System.out.println("Available cars:");
        clientService.getClientCars(Session.getInstance().getUser());
        System.out.println("Enter the ID of the car you want to order:");
        String carIdStr = scanner.nextLine();
        int carId;
        try {
            carId = Integer.parseInt(carIdStr);
        } catch (ClassCastException e) {
            ErrorResponses.printCustomMessage("Invalid car ID format.");
            return null;
        }
        return carService.getCarById(carId);
    }

    @Override
    public void update() {
        Order order = null;
        while (order == null) {
            System.out.println("Input please, id of request you want to update: ");
            String inputDeleteId = scanner.nextLine().trim();
            if (checkForExit(inputDeleteId)) return;
            try {
                order = orderService.findOrderById(Integer.parseInt(inputDeleteId));
                System.out.println();
            } catch (Exception e) {
                ErrorResponses.printCustomMessage("Hey what's wrong with ID, I cannot find request");
                ErrorResponses.printCustomMessage("Type 'exit', to end process");
            }
        }

        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want to update this:");
        System.out.println(order + ConsoleColors.RESET);

        if (!confirm("Updating order....")) return;

        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        System.out.println("Updating order: " + order);

        // Update car
        System.out.println("Current car: " + order.getCar());
        System.out.println("Enter new car ID or press Enter to skip:");
        String carIdStr = scanner.nextLine();
        if (!carIdStr.isEmpty()) {
            int carId;
            try {
                carId = Integer.parseInt(carIdStr);
                Car newCar = carService.findCarById(carId);
                if (newCar != null) {
                    order.setCar(newCar);
                } else {
                    ErrorResponses.printCustomMessage("Car selection failed.");
                }
            } catch (IllegalArgumentException e) {
                ErrorResponses.printCustomMessage("Invalid car ID format.");
            }
        }



        // Update type of order
        System.out.println("Current type of order: " + order.getType());
        System.out.println("Enter new type of order or press Enter to skip:");
        for (Order.TypeOrder type : Order.TypeOrder.values()) {
            System.out.println(type.ordinal() + ": " + type);
        }
        String typeIndexStr = scanner.nextLine();
        if (!typeIndexStr.isEmpty()) {
            try {
                int typeIndex = Integer.parseInt(typeIndexStr);
                if (typeIndex >= 0 && typeIndex < Order.TypeOrder.values().length) {
                    order.setType(Order.TypeOrder.values()[typeIndex]);
                } else {
                    ErrorResponses.printCustomMessage("Invalid type of order.");
                }
            } catch (NumberFormatException e) {
                ErrorResponses.printCustomMessage("Invalid type of order format.");
            }
        }

        // Update note
        System.out.println("Current note: " + order.getNote());
        System.out.println("Enter new note or press Enter to skip:");
        String note = scanner.nextLine();
        if (!note.isEmpty()) {
            order.setNote(note);
        }

        // Update status
        System.out.println("Current status of order: " + order.getStatus());
        System.out.println("Enter new status of order or press Enter to skip:");
        for (Order.OrderStatus status : Order.OrderStatus.values()) {
            System.out.println(status.ordinal() + ": " + status);
        }
        String statusIndexStr = scanner.nextLine();
        if (!statusIndexStr.isEmpty()) {
            try {
                int statusIndex = Integer.parseInt(statusIndexStr);
                if (statusIndex >= 0 && statusIndex < Order.OrderStatus.values().length) {
                    order.setStatus(Order.OrderStatus.values()[statusIndex]);
                } else {
                    ErrorResponses.printCustomMessage("Invalid status of order.");
                }
            } catch (NumberFormatException e) {
                ErrorResponses.printCustomMessage("Invalid status of order format.");
            }
        }

        orderService.updateOrder(order);

        SuccessResponses.printCustomMessage("Order updated successfully.");
    }

    public void cancel(){
        System.out.println("Input please, id of request you want to cancel: ");
        String inputCancelId = scanner.nextLine().trim();
        Order orderCancel = orderService.findOrderById(Integer.parseInt(inputCancelId));
        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want cancel this:");
        System.out.println(orderCancel + ConsoleColors.RESET);
        if (!confirm("Request canceled.")) return;
        orderService.cancelOrder(orderCancel);
    }

    @Override
    public void delete() {

    }

    public void takeRequestForManager() {
        System.out.println("\uD83C\uDD99 Taking request");
        System.out.println("Input please, id of request you want to take: ");
        String inputDeleteId = scanner.nextLine().trim();
        Order requestForTake = orderService.findOrderById(Integer.parseInt(inputDeleteId));
        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want to take this:");
        System.out.println(requestForTake + ConsoleColors.RESET);
        if (!confirm("Request taken")) return;
        requestForTake.setManager(Session.getInstance().getUser());
        orderService.updateOrder(requestForTake);
    }

    @Override
    public void search() {
        String query = orderService.formingQuerySearchOrders(Order.TypeOrder.SERVICE);
        printList(orderService.getOrdersBySearch(query));
    }

    public void viewByClient() {
        printList(clientService.getClientRequests(Session.getInstance().getUser()));
    }

    public void viewByManager(){
        printList(managerService.getManagerTakenRequests(Session.getInstance().getUser()));
    }

    @Override
    public void viewAll() {
        orderService.getAllServiceOrders();
    }
}
