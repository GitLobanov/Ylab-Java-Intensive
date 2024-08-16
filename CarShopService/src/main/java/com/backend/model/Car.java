package com.backend.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Car {

    private int id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String condition;
    private String color;
    private boolean availability;

    public Car (){
    }

    public Car (int id, String brand, String model,int year, double price, String condition, String color, boolean availability){
        setId(id);
        setBrand(brand);
        setModel(model);
        setYear(year);
        setPrice(price);
        setCondition(condition);
        setColor(color);
        setAvailability(availability);
    }

}
