package com.backend.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Order {

    private UUID id;
    private Car car;
    private User client;
    private LocalDateTime orderDateTime;
    private OrderStatus status;
    private TypeOrder type;
    private String note;
    private User manager;

    public enum OrderStatus {
        PENDING, COMPLETED, CANCELLED
    }

    public enum TypeOrder {
        BUYING, SERVICE
    }

    public Order(Car car, User client, TypeOrder type, String note) {
        setId(UUID.randomUUID());
        setCar(car);
        setClient(client);
        setOrderDateTime(LocalDateTime.now());
        setStatus(OrderStatus.PENDING);
        setType(type);
        setNote(note);
    }

    @Override
    public String toString() {
        return "Order: "+ getId() + " {" +
                "\ncar= [" + car.getId() + ", " + car.getModel() + ", " + car.getBrand() + "]" +
                "\nclient= [" + client.getUserName() + ", " + client.getName() + "]" +
                "\norderDateTime=" + orderDateTime +
                ", status=" + status +
                ", type=" + type +
                ", note='" + note + '\'' +
                '}';
    }
}
