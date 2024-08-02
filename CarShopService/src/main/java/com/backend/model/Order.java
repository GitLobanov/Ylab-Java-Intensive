package com.backend.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {

    private int id;
    private Car car;
    private User client;
    private LocalDateTime orderDateTime;
    private OrderStatus status;
    public String note;

    public enum OrderStatus {
        PENDING, COMPLETED, CANCELLED
    }

}
