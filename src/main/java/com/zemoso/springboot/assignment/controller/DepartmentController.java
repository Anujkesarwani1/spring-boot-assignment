package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.DTO.DepartmentDTO;
import com.zemoso.springboot.assignment.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // add mapping for GET /departments
    @GetMapping("/")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        List<DepartmentDTO> departmentDTOs = departmentService.getAllDepartments();
        return ResponseEntity.ok(departmentDTOs);
    }

    // add mapping for GET /departments/{departmentId}
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(departmentDTO);
    }

    // add mapping for POST /departments - add new department
    @PostMapping("/")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        System.out.println(departmentDTO.toString());
        DepartmentDTO createDepartmentDTo = departmentService.createDepartment(departmentDTO);
        return ResponseEntity.ok(createDepartmentDTo);
    }

    // add mapping for UPDATE /departments/{departmentId} - update existing departments
    @PutMapping("/")
    public ResponseEntity<DepartmentDTO> updateDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updatedDepartmentDTO = departmentService.updateDepartment(departmentDTO);
        return ResponseEntity.ok(updatedDepartmentDTO);
    }

    // add mapping for DELETE /departments/{departmentId} - delete departments by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
