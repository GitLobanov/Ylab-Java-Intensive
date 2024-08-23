package com.backend.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("/swagger")
public class SwaggerController {

    @GetMapping
    public String swaggerUi() {
        System.out.println("Swagger UI");
        return "index";
    }

    @GetMapping("/resp")
    public ResponseEntity<String> responseEntity() throws IOException {
        Resource resource = new ClassPathResource("static/index.html");
        String content = new String(Files.readAllBytes(resource.getFile().toPath()));
        return new ResponseEntity<>(content, HttpStatus.OK);
    }
}