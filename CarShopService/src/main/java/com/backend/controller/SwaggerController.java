package com.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {

    @RequestMapping("/swagger-ui.html")
    public String swaggerUi() {
        return "redirect:/webjars/swagger-ui/index.html";
    }
}