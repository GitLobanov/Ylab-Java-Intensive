package com.backend.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
public class User {

    private int id;
    private String username;
    private String password;
    private Role role;
    private String name;
    private String email;
    private String phone;

    public enum Role {
        ADMIN, CLIENT, MANAGER
    }

    public User (int id, String username, String password, Role role, String name, String email, String phone){
        setId(id);
        setUsername(username);
        setPassword(password);
        setRole(role);
        setName(name);
        setEmail(email);
        setPhone(phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
