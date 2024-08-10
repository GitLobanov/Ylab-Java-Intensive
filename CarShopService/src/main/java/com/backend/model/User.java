package com.backend.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class User {

    private UUID id;
    private String userName;
    private String password;
    private Role role;
    private String name;
    // contact info
    private String email;
    private String phone;

    public enum Role {
        ADMIN, CLIENT, MANAGER
    }

    public User (String userName, String password, Role role, String name, String email, String phone){
        setId(UUID.randomUUID());
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
