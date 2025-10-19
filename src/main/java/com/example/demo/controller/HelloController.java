package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello DevOps!";
    }
    @GetMapping("/health")
    public String health() {
        return "Application is healthy!";
    }
}
