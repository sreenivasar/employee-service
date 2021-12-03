package com.demo.employee.controller;

import com.demo.employee.exception.DataNotFoundException;
import com.demo.employee.models.Employee;
import com.demo.employee.repository.EmployeeRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/")
    private String home() {
        return "Welcome to Employee REST API service!!!!";
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            if (CollectionUtils.isNotEmpty(employees)) {
                return new ResponseEntity<>(employees, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws DataNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new DataNotFoundException("Employee details not found for the provided employee id: " + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/employee")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetails) throws DataNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new DataNotFoundException("Employee details not found for the provided employee id: " + employeeId));

        employee.setLastName(employeeDetails.getLastName());
        employee.setMiddleName(employeeDetails.getMiddleName());
        employee.setFirstName(employeeDetails.getFirstName());

        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws DataNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new DataNotFoundException("Employee details not found for the provided employee id: " + employeeId));

        employeeRepository.delete(employee);
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }

}
