package com.backend.dto;

import com.backend.model.Car;
import com.backend.model.Order;
import com.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private int id;
    private Car car;
    private User client;
    private Date orderDate;
    private Order.OrderStatus status;
    private Order.TypeOrder type;
    private String note;
    private User manager;

}
