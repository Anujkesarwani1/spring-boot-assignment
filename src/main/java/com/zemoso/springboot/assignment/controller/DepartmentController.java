package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.dto.DepartmentDTO;
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

    @GetMapping("/")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        List<DepartmentDTO> departmentDTOs = departmentService.getAllDepartments();
        return ResponseEntity.ok(departmentDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(departmentDTO);
    }

    @PostMapping("/")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO createDepartmentDTo = departmentService.createDepartment(departmentDTO);
        return ResponseEntity.ok(createDepartmentDTo);
    }

    @PutMapping("/")
    public ResponseEntity<DepartmentDTO> updateDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updatedDepartmentDTO = departmentService.updateDepartment(departmentDTO);
        return ResponseEntity.ok(updatedDepartmentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
