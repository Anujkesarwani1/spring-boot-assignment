package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.DTO.DepartmentDTO;
import com.zemoso.springboot.assignment.entity.Department;
import com.zemoso.springboot.assignment.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Department not found with id " + id));
        System.out.println(department.toString());
        return convertToDto(department);
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return convertToDto(savedDepartment);
    }

    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) {
        Department existingDepartment = departmentRepository.findById(departmentDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Department not found with id " + departmentDTO.getId()));

        existingDepartment.setDepartmentName(departmentDTO.getDepartmentName());
        existingDepartment.setDepartmentAddress(departmentDTO.getDepartmentAddress());
        existingDepartment.setDepartmentCode(departmentDTO.getDepartmentCode());
        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return convertToDto(updatedDepartment);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    private DepartmentDTO convertToDto(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setDepartmentName(department.getDepartmentName());
        departmentDTO.setDepartmentAddress(department.getDepartmentAddress());
        departmentDTO.setDepartmentCode(department.getDepartmentCode());
        return departmentDTO;
    }
    private Department convertToEntity(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setDepartmentName(departmentDTO.getDepartmentName());
        department.setDepartmentAddress(departmentDTO.getDepartmentAddress());
        department.setDepartmentCode(departmentDTO.getDepartmentCode());
        return department;
    }
}