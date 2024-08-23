package com.backend.controller;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v3")
public class ApiDocsController {

    @Autowired
    OpenAPI customOpenAPI;

    @GetMapping("/api-docs")
    public OpenAPI apiDocs() {
        return customOpenAPI;
    }
}