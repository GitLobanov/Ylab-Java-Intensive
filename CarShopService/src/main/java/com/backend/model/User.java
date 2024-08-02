package com.backend.model;


import lombok.Data;

@Data
public abstract class User {

    private int id;
    private String userName;
    private String password;
    private Role role;
    private String name;
    // contact info
    private String email;
    private String phone;

    public enum Role {
        ADMIN, USER, MANAGER
    }

}
