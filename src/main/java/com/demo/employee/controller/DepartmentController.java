package com.demo.employee.controller;

import com.demo.employee.exception.DataNotFoundException;
import com.demo.employee.models.Department;
import com.demo.employee.repository.DepartmentRepository;
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
import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        try {
            List<Department> departments = departmentRepository.findAll();
            if (CollectionUtils.isNotEmpty(departments)) {
                return new ResponseEntity<>(departments, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable(value = "id") Long departmentId)
            throws DataNotFoundException {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DataNotFoundException("Department details not found for the provided department id: " + departmentId));
        return ResponseEntity.ok().body(department);
    }

    @PostMapping("/department")
    public Department createDepartment(@Valid @RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable(value = "id") Long departmentId,
                                                   @Valid @RequestBody Department departmentDetails) throws DataNotFoundException {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DataNotFoundException("Department details not found for the provided department id: " + departmentId));

        department.setDepartmentName(departmentDetails.getDepartmentName());
        department.setDepartmentId(departmentId);

        final Department updatedDepartment = departmentRepository.save(department);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/department/{id}")
    public ResponseEntity deleteDepartment(@PathVariable(value = "id") Long departmentId)
            throws DataNotFoundException {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DataNotFoundException("Department details not found for the provided department id: " + departmentId));

        departmentRepository.delete(department);
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }

}
