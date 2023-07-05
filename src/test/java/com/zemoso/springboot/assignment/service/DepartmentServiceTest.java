package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.dto.DepartmentDTO;
import com.zemoso.springboot.assignment.entity.Department;
import com.zemoso.springboot.assignment.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDepartments_ReturnsListOfDepartments() {
        // Arrange
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department(1L, "HR", "Address 1", "HR-001"));
        departmentList.add(new Department(2L, "Finance", "Address 2", "FIN-001"));

        when(departmentRepository.findAll()).thenReturn(departmentList);

        // Act
        List<DepartmentDTO> result = departmentService.getAllDepartments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("HR", result.get(0).getDepartmentName());
        assertEquals("Address 1", result.get(0).getDepartmentAddress());
        assertEquals("HR-001", result.get(0).getDepartmentCode());
        // Verify that the repository method was called
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void createDepartment_ValidInput_ReturnsCreatedDepartmentDTO() {
        // Arrange
        DepartmentDTO departmentDTO = new DepartmentDTO(null, "HR", "Address 1", "HR-001");
        Department createdDepartment = new Department(1L, "HR", "Address 1", "HR-001");

        when(departmentRepository.save(any(Department.class))).thenReturn(createdDepartment);

        // Act
        DepartmentDTO result = departmentService.createDepartment(departmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("HR", result.getDepartmentName());
        assertEquals("Address 1", result.getDepartmentAddress());
        assertEquals("HR-001", result.getDepartmentCode());
        // Verify that the repository method was called
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void updateDepartment_ValidInput_ReturnsUpdatedDepartmentDTO() {
        // Arrange
        DepartmentDTO departmentDTO = new DepartmentDTO(1L, "HR", "Address 1", "HR-001");
        Department existingDepartment = new Department(1L, "HR", "Address 1", "HR-001");
        Department updatedDepartment = new Department(1L, "HR", "Address 2", "HR-002");

        when(departmentRepository.findById(departmentDTO.getId())).thenReturn(Optional.of(existingDepartment));
        when(departmentRepository.save(any(Department.class))).thenReturn(updatedDepartment);

        // Act
        DepartmentDTO result = departmentService.updateDepartment(departmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("HR", result.getDepartmentName());
        assertEquals("Address 2", result.getDepartmentAddress());
        assertEquals("HR-002", result.getDepartmentCode());
        // Verify that the repository method was called
        verify(departmentRepository, times(1)).findById(departmentDTO.getId());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void deleteDepartment_ExistingId_DeletesDepartment() {
        // Arrange
        Long departmentId = 1L;

        // Act
        departmentService.deleteDepartment(departmentId);

        // Verify that the repository method was called
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }
}
