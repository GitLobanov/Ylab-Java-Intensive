package com.backend.view.handler;

import com.backend.model.Car;
import com.backend.service.CarService;
import com.backend.service.impl.ClientService;
import com.backend.util.ConsoleColors;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.util.SuccessResponses;

public class CarMenuHandler extends BaseHandler {

    CarService carService = new CarService();
    ClientService clientService = new ClientService();

    @Override
    public void handlerMenu() {

    }

    @Override
    public void create() {
        System.out.println("\uD83C\uDD95 Create a new car");

        String color = getField("Color: ");
        String availabilityInput = getField("Availability (true/yes or false/no) ");
        boolean availability = availabilityInput.equals("yes") || availabilityInput.equals("true");
        String model = getField("Model: ");
        String brand = getField("Brand: ");
        int year = Integer.parseInt(getField("Year: "));
        double price = Double.parseDouble(getField("price"));
        String condition = getField("Condition (new/old): ");

        Car car = new Car(0, brand, model, year, price, condition, color, availability);

        carService.addCar(car);
        SuccessResponses.printCustomMessage("Woo! We have new car!");
    }

    @Override
    public void update() {
        System.out.println("\uD83C\uDD99 Update a car");

        System.out.println("Input please, id of car you want to update: ");
        String inputUpdateId = scanner.nextLine().trim();
        Car carForUpdate = carService.findCarById(Integer.parseInt(inputUpdateId));

        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want update this car :");
        System.out.println(carForUpdate.toString() + ConsoleColors.RESET);

        if (!confirm("Beginning update info...")) return;

        System.out.println("Input info about car (if you don't wanna update some fields, just leave they empty):");

        System.out.println("color (was " + carForUpdate.getColor() + "): ");
        String colorInput = scanner.nextLine().trim();
        if (!colorInput.isEmpty()) {
            carForUpdate.setColor(colorInput);
        }

        System.out.println("availability (yes/no): ");
        String availabilityUpdateInput = scanner.nextLine().trim();
        if (!availabilityUpdateInput.isEmpty()) {
            carForUpdate.setAvailability(availabilityUpdateInput.equalsIgnoreCase("yes") || availabilityUpdateInput.equalsIgnoreCase("true"));
        }

        System.out.println("model (was " + carForUpdate.getModel() + "): ");
        String modelInput = scanner.nextLine().trim();
        if (!modelInput.isEmpty()) {
            carForUpdate.setModel(modelInput);
        }

        System.out.println("brand (was " + carForUpdate.getBrand() + "): ");
        String brandInput = scanner.nextLine().trim();
        if (!brandInput.isEmpty()) {
            carForUpdate.setBrand(brandInput);
        }

        System.out.println("year (was " + carForUpdate.getYear() + "): ");
        String yearInput = scanner.nextLine().trim();
        if (!yearInput.isEmpty()) {
            int yearUpdate = Integer.parseInt(yearInput);
            carForUpdate.setYear(yearUpdate);
        }

        System.out.println("price (was " + carForUpdate.getPrice() + "): ");
        String priceInput = scanner.nextLine().trim();
        if (!priceInput.isEmpty()) {
            double priceUpdate = Double.parseDouble(priceInput);
            carForUpdate.setPrice(priceUpdate);
        }

        System.out.println("condition: ");
        String conditionInput = scanner.nextLine().trim();
        if (!conditionInput.isEmpty()) {
            carForUpdate.setCondition(conditionInput);
        }

        carService.updateCar(carForUpdate);
        SuccessResponses.printCustomMessage("Car updated successfully.");
    }

    @Override
    public void delete() {
        Car carForDeleting = selectCar();
        if (carForDeleting == null) {
            ErrorResponses.printCustomMessage("I don't have that car");
            return;
        }
        System.out.println(ConsoleColors.YELLOW_BOLD + "\uD83D\uDC40 You sure, what do you want delete this car :");
        System.out.println(carForDeleting.toString() + ConsoleColors.RESET);

        if (confirm("Car deleted.")) carService.deleteCar(carForDeleting);
    }

    @Override
    public void search() {
        String query = carService.formingQuerySearchCars();
        printList(carService.getCarsBySearch(query));
    }

    public void viewByClient(){
        printList(clientService.getClientCars(Session.getInstance().getUser()));
    }

    public void viewAll() {
        printList(carService.getAllCars());
    }
}
