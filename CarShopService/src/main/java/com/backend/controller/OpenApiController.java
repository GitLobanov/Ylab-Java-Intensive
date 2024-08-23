package com.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/api-docs")
public class OpenApiController {

    private final ObjectMapper objectMapper;

    public OpenApiController() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @GetMapping(value = "/openapi")
    public ResponseEntity<String> getOpenApiYaml() throws IOException {
        Resource resource = new ClassPathResource("static/api.yaml");
        String content = new String(Files.readAllBytes(resource.getFile().toPath()));
        return new ResponseEntity<>(content, HttpStatus.OK);
    }
}