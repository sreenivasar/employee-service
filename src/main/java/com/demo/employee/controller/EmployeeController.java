package com.demo.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @GetMapping("/")
    private String home() {
        return "Welcome to Employee REST API service!!!!";
    }

}
