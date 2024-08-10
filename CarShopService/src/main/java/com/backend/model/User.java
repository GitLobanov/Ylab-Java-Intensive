package com.backend.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class User {

    private int id;
    private String userName;
    private String password;
    private String role;
    private String name;
    private String email;
    private String phone;

    public User (int id, String userName, String password, String role, String name, String email, String phone){
        setId(id);
        setUserName(userName);
        setPassword(password);
        setRole(role);
        setName(name);
        setEmail(email);
        setPhone(phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
