package com.backend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class CarDTO {

    int id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String condition;
    private String color;
    private boolean availability;

}
