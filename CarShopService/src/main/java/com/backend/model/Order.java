package com.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Order {

    private int id;
    private Car car;
    private User client;
    private String orderDateTime;
    private OrderStatus status;
    private TypeOrder type;
    private String note;
    private User manager;

    public Order(int id, Car car, User client, String orderDateTime, OrderStatus status, TypeOrder type, String note, User manager) {
        setId(id);
        setCar(car);
        setClient(client);
        setOrderDateTime(orderDateTime);
        setStatus(status);
        setType(type);
        setNote(note);
    }

    public enum OrderStatus {
        PENDING, COMPLETED, CANCELLED
    }

    public enum TypeOrder {
        BUYING, SERVICE
    }

    public Order(int id, Car car, User client, TypeOrder type, String note) {
        setId(id);
        setCar(car);
        setClient(client);
        setOrderDateTime(LocalDate.now().toString());
        setStatus(OrderStatus.PENDING);
        setType(type);
        setNote(note);
    }

    @Override
    public String toString() {
        return "Order: "+ getId() + " {" +
                "\ncar= ["+ car.getModel() + ", " + car.getBrand() + "]" +
                "\nclient= [" + client.getUserName() + ", " + client.getName() + "]" +
                "\norderDateTime=" + orderDateTime +
                ", status=" + status +
                ", type=" + type +
                ", note='" + note + '\'' +
                '}';
    }
}
