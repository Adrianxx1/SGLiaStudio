package com.liastudio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liastudio.repository.TestRepository;
import com.liastudio.model.TestEntity;

@RestController
public class TestController {

    private final TestRepository repository;

    public TestController(TestRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/test")
    public String testConnection() {
        repository.save(new TestEntity("Conexión exitosa con MySQL 🎉"));
        long count = repository.count();
        return "✅ Conexión establecida. Registros en la tabla: " + count;
    }
}
