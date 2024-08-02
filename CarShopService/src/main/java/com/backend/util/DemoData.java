package com.backend.util;

import com.backend.model.Car;
import com.backend.model.User;
import com.backend.repository.CarRepository;
import com.backend.repository.UserRepository;

public class DemoData {

    public void loadData () {

        User client = new User("john_doe", "123", User.Role.CLIENT, "John Doe", "john.doe@example.com", "123-456-7890");
        User manager = new User("john_man", "123", User.Role.MANAGER, "John Doe", "john.doe@example.com", "123-456-7890");
        User admin = new User("john_adman", "123", User.Role.ADMIN, "John Doe", "john.doe@example.com", "123-456-7890");

        UserRepository.getInstance().save(client);
        UserRepository.getInstance().save(manager);
        UserRepository.getInstance().save(admin);

        CarRepository.getInstance().save(new Car("Toyota", "Camry", 2020, 20000.00, "New", "White", true));
        CarRepository.getInstance().save(new Car("Honda", "Civic", 2019, 18000.00, "Used", "Black", true));
        CarRepository.getInstance().save(new Car("Ford", "Mustang", 2021, 30000.00, "New", "Red", true));
        CarRepository.getInstance().save(new Car("Chevrolet", "Malibu", 2018, 16000.00, "Used", "Blue", false));
        CarRepository.getInstance().save(new Car("BMW", "X5", 2022, 50000.00, "New", "Silver", true));
        CarRepository.getInstance().save(new Car("Audi", "A4", 2021, 35000.00, "New", "Gray", true));
        CarRepository.getInstance().save(new Car("Hyundai", "Elantra", 2017, 14000.00, "Used", "Green", false));
        CarRepository.getInstance().save(new Car("Nissan", "Altima", 2020, 19000.00, "Used", "Orange", true));
        CarRepository.getInstance().save(new Car("Subaru", "Outback", 2019, 22000.00, "Used", "Brown", true));
        CarRepository.getInstance().save(new Car("Volkswagen", "Golf", 2022, 25000.00, "New", "Purple", true));


    }

}
