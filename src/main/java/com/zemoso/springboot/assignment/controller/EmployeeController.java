package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.dto.EmployeeDTO;
import com.zemoso.springboot.assignment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // add mapping for GET /departments
    @GetMapping("/")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeDTOS);
    }

    // add mapping for GET /departments/{departmentId}
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDTO);
    }

    // add mapping for POST /departments - add new department
    @PostMapping("/")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createEmployeeDTO = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok(createEmployeeDTO);
    }

    // add mapping for UPDATE /departments/{departmentId} - update existing departments
    @PutMapping("/")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    // add mapping for DELETE /departments/{departmentId} - delete departments by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
