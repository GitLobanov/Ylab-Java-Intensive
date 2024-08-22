package com.backend.dto;

import com.backend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDTO {

    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private User.Role role;

}

