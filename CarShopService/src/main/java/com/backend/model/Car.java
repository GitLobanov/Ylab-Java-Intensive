package com.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
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

}
