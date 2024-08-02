package com.backend.model;

import lombok.Data;

@Data
public class Car {

    private String brand;
    private String model;
    private int year;
    private double price;
    private String condition;
    private String color;
    private boolean availability;

}
