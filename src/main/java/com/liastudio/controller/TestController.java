package com.liastudio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    
    @GetMapping("/test")
    public String test() {
        return "¡Endpoint protegido! Solo accesible con JWT válido.";
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "¡Hola desde Spring Boot! Este endpoint también está protegido.";
    }
}