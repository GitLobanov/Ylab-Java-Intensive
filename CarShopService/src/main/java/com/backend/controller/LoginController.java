package com.backend.controller;

import com.backend.dto.LoginDTO;
import com.backend.model.User;
import com.backend.service.LoginRegisterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginRegisterService loginRegisterService;

    @Autowired
    public LoginController(LoginRegisterService loginRegisterService) {
        this.loginRegisterService = loginRegisterService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        User currentUser = loginRegisterService.login(username, password);

        if (currentUser != null) {
            session.setAttribute("currentUser", currentUser);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
