package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import com.backend.repository.impl.ActionLogRepository;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.OrderRepository;
import com.backend.repository.impl.UserRepository;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.util.SuccessResponses;
import com.backend.util.ActionLogExporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public abstract class UserAbstractService {

    Scanner scanner = new Scanner(System.in);
    UserRepository userRepository = new UserRepository();
    CarRepository carRepository = new CarRepository();
    OrderRepository orderRepository = new OrderRepository();
    ActionLogRepository actionLogRepository = new ActionLogRepository();

    public boolean addOrder(Order order) {
        log(ActionLog.ActionType.CREATE, "Created order");
        checkCarForAvailability(order);
        return orderRepository.save(order);
    }


    public boolean updateOrder(Order order) {
        log(ActionLog.ActionType.UPDATE, "Updated order");
        checkCarForAvailability(order);
        return orderRepository.update(order);
    }


    public Order findOrderById(int id) {
        return orderRepository.findById(id);
    }



    public List<Order> findOrderByClientAndTypeOrder(User user, Order.TypeOrder typeOrder) {
        log(ActionLog.ActionType.VIEW, "Search client order");
        List<Order> result = orderRepository.findByClient(user);
        for (Order order : result) {
            if (order.getType() != typeOrder) {
                result.remove(order);
            }
        }
        return result;
    }



    public boolean cancelOrder(Order order) {
        log(ActionLog.ActionType.CANCEL, "Canceled order");
        order.setStatus(Order.OrderStatus.CANCELLED);
        return orderRepository.update(order);
    }



    public void viewAllCars() {
        log(ActionLog.ActionType.VIEW, "View all cars");
        Iterator<Car> iterator = carRepository.findAllAvailableCars().iterator();
        displaySearchResultCar(iterator);
    }


    private void checkCarForAvailability(Order order) {
        if (order.getType() == Order.TypeOrder.BUYING && order.getStatus() == Order.OrderStatus.COMPLETED) {
            Car car = order.getCar();
            car.setAvailability(false);
            carRepository.save(car);
        }
    }



    public void viewNotOrderedCars() {
        log(ActionLog.ActionType.VIEW, "View not ordered cars");
        Iterator<Car> iterator = carRepository.findAllAvailableCars().iterator();
        List<Car> list = new ArrayList<>();
        while (iterator.hasNext()) {
            Car car = iterator.next();
            if (orderRepository.findByCar(car) == null) list.add(car);
        }

        displaySearchResultCar(iterator);
    }



    public void searchCars(String query) {

        log(ActionLog.ActionType.VIEW, "Search cars");

        List<Car> cars = carRepository.findAllAvailableCars();
        String[] params = query.split(";");

        for (String param : params) {
            String[] keyValue = param.split(":");

            if (keyValue.length < 2) {
                continue;
            }

            String key = keyValue[0];
            String value = keyValue[1];



            switch (key) {
                case "brand":
                    cars = cars.stream()
                            .filter(car -> car.getBrand().equalsIgnoreCase(value))
                            .collect(Collectors.toList());
                    break;
                case "color":
                    cars = cars.stream()
                            .filter(car -> car.getColor().equalsIgnoreCase(value))
                            .collect(Collectors.toList());
                    break;
                case "priceFrom<price<priceTo":
                    String[] priceRange = value.split("<price<");
                    double priceRangeFrom = Double.parseDouble(priceRange[0]);
                    double priceRangeTo = Double.parseDouble(priceRange[1]);
                    cars = cars.stream()
                            .filter(car -> car.getPrice() >= priceRangeFrom && car.getPrice() <= priceRangeTo)
                            .collect(Collectors.toList());
                    break;
                case "priceFrom<price":
                    String[] priceOnlyFrom = value.split("<price");
                    double priceFrom = Double.parseDouble(priceOnlyFrom[0]);
                    cars = cars.stream()
                            .filter(car -> car.getPrice() >= priceFrom)
                            .collect(Collectors.toList());
                    break;
                case "price<priceTo":
                    String[] priceOnlyTo = value.split("price<");
                    double priceTo = Double.parseDouble(priceOnlyTo[0]);
                    cars = cars.stream()
                            .filter(car -> car.getPrice() <= priceTo)
                            .collect(Collectors.toList());
                    break;
                case "model":
                    cars = cars.stream()
                            .filter(car -> car.getModel().equalsIgnoreCase(value))
                            .collect(Collectors.toList());
                    break;
                case "yearFrom<year<yearTo":
                    String[] yearRange = value.split("<year<");
                    int yearRangeFrom = Integer.parseInt(yearRange[0]);
                    int yearRangeTo = Integer.parseInt(yearRange[1]);
                    cars = cars.stream()
                            .filter(car -> car.getYear() >= yearRangeFrom && car.getYear() <= yearRangeTo)
                            .collect(Collectors.toList());
                    break;
                case "yearFrom<year":
                    String[] yearOnlyFrom = value.split("<year<");
                    int yearFrom = Integer.parseInt(yearOnlyFrom[0]);
                    cars = cars.stream()
                            .filter(car -> car.getYear() >= yearFrom)
                            .collect(Collectors.toList());
                    break;
                case "year<yearTo":
                    String[] yearOnlyTo = value.split("<year<");
                    int yearTo = Integer.parseInt(yearOnlyTo[0]);
                    cars = cars.stream()
                            .filter(car -> car.getYear() <= yearTo)
                            .collect(Collectors.toList());
                    break;
                case "availability":
                    boolean availability = Boolean.parseBoolean(value);
                    cars = cars.stream()
                            .filter(car -> car.isAvailability() == availability)
                            .collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }

        Iterator<Car> iterator = cars.iterator();
        displaySearchResultCar(iterator);
    }


    // forming query: brand:toyota;color:white;priceFrom<price<priceTo;model:xr200;yearFrom<2000<yearTo;availability:true
    public String formingQuerySearchCars (){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input parameters (if field doesn't need leave it blank)");

        System.out.println("Brand (example, toyota): ");
        String brand = scanner.nextLine().trim();

        System.out.println("Color (example, white): ");
        String color = scanner.nextLine().trim();

        System.out.println("Min price (leave it blank, if field doesn't need leave it blank): ");
        String priceFrom = scanner.nextLine().trim();

        System.out.println("Max price (leave it blank, if field doesn't need leave it blank): ");
        String priceTo = scanner.nextLine().trim();

        System.out.println("Model (leave it blank, if field doesn't need leave it blank): : ");
        String model = scanner.nextLine().trim();

        System.out.println("Year from (оставьте пустым, если не нужно): ");
        String yearFrom = scanner.nextLine().trim();

        System.out.println("Year to (leave it blank, if field doesn't need leave it blank): ");
        String yearTo = scanner.nextLine().trim();

        System.out.println("Availability (true/false, оставьте пустым, если не нужно): ");
        String availability = scanner.nextLine().trim();

        // Создание строки запроса
        StringBuilder queryBuilder = new StringBuilder();

        if (!brand.isEmpty()) {
            queryBuilder.append("brand:").append(brand).append(";");
        }

        if (!color.isEmpty()) {
            queryBuilder.append("color:").append(color).append(";");
        }

        if (!priceFrom.isEmpty() && !priceTo.isEmpty()) {
            queryBuilder.append("priceFrom<price<priceTo:").append(priceFrom).append("<price<").append(priceTo).append(";");
        } else if (!priceFrom.isEmpty()) {
            queryBuilder.append("priceFrom<price:").append(priceFrom).append(";");
        } else if (!priceTo.isEmpty()) {
            queryBuilder.append("price<priceTo:").append(priceTo).append(";");
        }

        if (!model.isEmpty()) {
            queryBuilder.append("model:").append(model).append(";");
        }

        if (!yearFrom.isEmpty() && !yearTo.isEmpty()) {
            queryBuilder.append("yearFrom<year<yearTo:").append(yearFrom).append("<year<").append(yearTo).append(";");
        } else if (!yearFrom.isEmpty()) {
            queryBuilder.append("yearFrom<year:").append(yearFrom).append(";");
        } else if (!yearTo.isEmpty()) {
            queryBuilder.append("year<yearTo:").append(yearTo).append(";");
        }

        if (!availability.isEmpty()) {
            queryBuilder.append("availability:").append(availability).append(";");
        }

        // Удаление последнего символа ';' если строка не пустая
        String query = queryBuilder.length() > 0 ? queryBuilder.substring(0, queryBuilder.length() - 1) : "";

        return query;

    }

    /**
     * Displays action log of user
     *
     * @param user the user for search logs
     */

    public void viewMyActionLog(User user) {
        List<ActionLog> actionLogList = actionLogRepository.findByUser(user);
        displaySearchResultActionLog(actionLogList.iterator());
    }

    /**
     * Displays search results for users.
     *
     * @param iterator the iterator over the search results
     */

    public static void displaySearchResultUser(Iterator<User> iterator) {
        if (!iterator.hasNext()) {
            ErrorResponses.printCustomMessage("Sorry. I can't find anything at all");
        } else {
            System.out.println("\nFound: ");
            while (iterator.hasNext()) {
                System.out.println(ConsoleColors.PURPLE_BOLD + iterator.next() + ConsoleColors.RESET);
                System.out.println("[-------------------------------------------------------]");
            }
        }
    }


    public void displaySearchResultActionLog(Iterator<ActionLog> iterator) {
        if (!iterator.hasNext()) {
            ErrorResponses.printCustomMessage("Sorry. I can't find anything at all");
        } else {
            System.out.println("\nFound: ");
            while (iterator.hasNext()) {
                System.out.println(ConsoleColors.PURPLE_BOLD + iterator.next() + ConsoleColors.RESET);
                System.out.println("[-------------------------------------------------------]");
            }
        }
    }



    public void displaySearchResultCar(Iterator<Car> iterator) {
        if (!iterator.hasNext()) {
            ErrorResponses.printCustomMessage("Sorry. I can't find any car at all");
        } else {
            System.out.println("\nFound: ");
            while (iterator.hasNext()) {
                System.out.println(ConsoleColors.PURPLE_BOLD + iterator.next() + ConsoleColors.RESET);
                System.out.println("[-------------------------------------------------------]");
            }
        }
    }



    public static void displaySearchResultOrder(Iterator<Map.Entry<UUID, Order>> iterator) {
        if (!iterator.hasNext()) {
            ErrorResponses.printCustomMessage("Hmm. Here is nothing!");
        } else {
            System.out.println("\nFound: ");
            while (iterator.hasNext()) {
                System.out.println(ConsoleColors.PURPLE_BOLD + iterator.next().getValue() + ConsoleColors.RESET);
                System.out.println("[-------------------------------------------------------]");
            }
        }
    }



    public int getClientOrderCount(User client) {
        return findOrderByClientAndTypeOrder(client, Order.TypeOrder.BUYING).size();
    }



    public int getClientServiceRequestCount(User client) {
        return findOrderByClientAndTypeOrder(client, Order.TypeOrder.SERVICE).size();
    }


    public void searchMyActionLog(String query, User user) {
        List<ActionLog> logs = actionLogRepository.findByUser(user);
        String[] params = query.split(";");

        for (String param : params) {
            String[] keyValue = param.split(":");

            if (keyValue.length < 2) {
                continue;
            }

            String key = keyValue[0];
            String value = keyValue[1];

            switch (key) {
                case "actionType":
                    logs = logs.stream()
                            .filter(log -> log.getActionType().toString().equalsIgnoreCase(value))
                            .collect(Collectors.toList());
                    break;
                case "actionDateTimeFrom<actionDateTime<actionDateTimeTo":
                    String[] dateTimeRange = value.split("<actionDateTime<");
                    LocalDateTime dateTimeFrom = LocalDateTime.parse(dateTimeRange[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDateTime dateTimeTo = LocalDateTime.parse(dateTimeRange[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    logs = logs.stream()
                            .filter(log -> log.getActionDateTime().isAfter(dateTimeFrom) && log.getActionDateTime().isBefore(dateTimeTo))
                            .collect(Collectors.toList());
                    break;
                case "actionDateTimeFrom<actionDateTime":
                    LocalDateTime dateTimeOnlyFrom = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    logs = logs.stream()
                            .filter(log -> log.getActionDateTime().isAfter(dateTimeOnlyFrom))
                            .collect(Collectors.toList());
                    break;
                case "actionDateTime<actionDateTimeTo":
                    LocalDateTime dateTimeOnlyTo = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    logs = logs.stream()
                            .filter(log -> log.getActionDateTime().isBefore(dateTimeOnlyTo))
                            .collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Forming query from user's input in console
     */

    public String formingQuerySearchMyActionLogs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input parameters (if field doesn't need leave it blank)");

        System.out.println("Action Type (CREATE/UPDATE/DELETE/CANCEL/VIEW/LOGIN/LOGOUT): ");
        String actionType = scanner.nextLine().trim();

        System.out.println("Action DateTime From (yyyy-MM-dd HH:mm:ss): ");
        String dateTimeFrom = scanner.nextLine().trim();

        System.out.println("Action DateTime To (yyyy-MM-dd HH:mm:ss): ");
        String dateTimeTo = scanner.nextLine().trim();

        StringBuilder queryBuilder = new StringBuilder();

        if (!actionType.isEmpty()) {
            queryBuilder.append("actionType:").append(actionType).append(";");
        }

        if (!dateTimeFrom.isEmpty() && !dateTimeTo.isEmpty()) {
            queryBuilder.append("actionDateTimeFrom<actionDateTime<actionDateTimeTo:")
                    .append(dateTimeFrom).append("<actionDateTime<").append(dateTimeTo).append(";");
        } else if (!dateTimeFrom.isEmpty()) {
            queryBuilder.append("actionDateTimeFrom<actionDateTime:").append(dateTimeFrom).append(";");
        } else if (!dateTimeTo.isEmpty()) {
            queryBuilder.append("actionDateTime<actionDateTimeTo:").append(dateTimeTo).append(";");
        }

        String query = queryBuilder.length() > 0 ? queryBuilder.substring(0, queryBuilder.length() - 1) : "";

        return query;
    }



    public void log(ActionLog.ActionType actionType, String message){
        actionLogRepository.save(new ActionLog(Session.getInstance().getUser(), actionType, message));
    }



    public void askUnloadActionLogToTXT (Map<UUID, ActionLog> actionLogs) {
        System.out.println("Do you want unload log to txt yes/y or not/n");
        String answer = scanner.nextLine().trim();
        if (answer.equals("yes") || answer.equals("y")) {
            SuccessResponses.printCustomMessage("Log unload in txt file");
            System.out.print("Name of file: ");
            String nameFile = scanner.nextLine();
            ActionLogExporter.exportToTextFile(actionLogs, nameFile);
        } else if (answer.equals("no") || answer.equals("n")) {
            SuccessResponses.printCustomMessage("\uD83E\uDD1D Okay, as you wish");
        } else {
            ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_UNKNOWN_COMMAND);
        }
    }

}
