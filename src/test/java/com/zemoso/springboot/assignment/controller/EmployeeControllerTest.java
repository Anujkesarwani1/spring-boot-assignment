package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.dto.EmployeeDTO;
import com.zemoso.springboot.assignment.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployee_ReturnsListOfEmployeeDTOs() {
        // Arrange
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        employeeDTOList.add(new EmployeeDTO(1L, "John", "Doe", "john.doe@example.com", 1L));
        employeeDTOList.add(new EmployeeDTO(2L, "Jane", "Smith", "jane.smith@example.com", 1L));

        when(employeeService.getAllEmployees()).thenReturn(employeeDTOList);

        // Act
        ResponseEntity<List<EmployeeDTO>> response = employeeController.getAllEmployee();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getFirstName());
        assertEquals("Doe", response.getBody().get(0).getLastName());
        assertEquals("john.doe@example.com", response.getBody().get(0).getEmail());
        // Verify that the service method was called
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void getEmployeeById_ExistingId_ReturnsEmployeeDTO() {
        // Arrange
        Long id = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO(id, "John", "Doe", "john.doe@example.com", 1L);

        when(employeeService.getEmployeeById(id)).thenReturn(employeeDTO);

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        // Verify that the service method was called
        verify(employeeService, times(1)).getEmployeeById(id);
    }

    @Test
    void getEmployeeById_NonexistentId_ReturnsNotFound() {
        // Arrange
        Long id = 1L;

        when(employeeService.getEmployeeById(id)).thenReturn(null);

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        // Verify that the service method was called
        verify(employeeService, times(1)).getEmployeeById(id);
    }

    @Test
    void createEmployee_ValidInput_ReturnsCreatedEmployeeDTO() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John", "Doe", "john.doe@example.com", 1L);
        EmployeeDTO createdEmployeeDTO = new EmployeeDTO(1L, "John", "Doe", "john.doe@example.com", 1L);

        when(employeeService.createEmployee(employeeDTO)).thenReturn(createdEmployeeDTO);

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.createEmployee(employeeDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        // Verify that the service method was called
        verify(employeeService, times(1)).createEmployee(employeeDTO);
    }

    @Test
    void updateEmployee_ValidInput_ReturnsUpdatedEmployeeDTO() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John", "Doe", "john.doe@example.com", 1L);
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO(1L, "John", "Doe", "john.doe@example.com", 1L);

        when(employeeService.updateEmployee(employeeDTO)).thenReturn(updatedEmployeeDTO);

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.updateEmployee(employeeDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        // Verify that the service method was called
        verify(employeeService, times(1)).updateEmployee(employeeDTO);
    }

    @Test
    void deleteEmployee_ExistingId_ReturnsNoContent() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = employeeController.deleteEmployeee(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        // Verify that the service method was called
        verify(employeeService, times(1)).deleteEmployee(id);
    }
}
