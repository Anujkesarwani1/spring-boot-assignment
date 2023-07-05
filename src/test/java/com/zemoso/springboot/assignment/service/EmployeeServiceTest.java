package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.dto.EmployeeDTO;
import com.zemoso.springboot.assignment.entity.Department;
import com.zemoso.springboot.assignment.entity.Employee;
import com.zemoso.springboot.assignment.repository.DepartmentRepository;
import com.zemoso.springboot.assignment.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees_ReturnsListOfEmployees() {
        // Arrange
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "John", "Doe", "john.doe@example.com", new Department()));
        employeeList.add(new Employee(2L, "Jane", "Smith", "jane.smith@example.com", new Department()));

        when(employeeRepository.findAll()).thenReturn(employeeList);

        // Act
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
        // Verify that the repository method was called
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeById_ExistingId_ReturnsEmployeeDTO() {
        // Arrange
        Long id = 1L;
        Employee employee = new Employee(id, "John", "Doe", "john.doe@example.com", new Department());

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        // Act
        EmployeeDTO result = employeeService.getEmployeeById(id);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        // Verify that the repository method was called
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    void getEmployeeById_NonexistentId_ThrowsNoSuchElementException() {
        // Arrange
        Long id = 1L;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> employeeService.getEmployeeById(id));
        // Verify that the repository method was called
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    void createEmployee_ValidInput_ReturnsCreatedEmployeeDTO() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO(null, "John", "Doe", "john.doe@example.com", 1L);
        Department department = new Department();
        Employee createdEmployee = new Employee(1L, "John", "Doe", "john.doe@example.com", department);

        when(departmentRepository.findById(employeeDTO.getDepartmentId())).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(createdEmployee);

        // Act
        EmployeeDTO result = employeeService.createEmployee(employeeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(null, result.getDepartmentId());
        // Verify that the repository methods were called
        verify(departmentRepository, times(1)).findById(employeeDTO.getDepartmentId());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_ValidInput_ReturnsUpdatedEmployeeDTO() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John", "Doe", "john.doe@example.com", 1L);
        Employee existingEmployee = new Employee(1L, "John", "Doe", "john.doe@example.com", new Department());
        Department department = new Department();
        Employee updatedEmployee = new Employee(1L, "John", "Doe", "john.doe@example.com", department);

        when(employeeRepository.findById(employeeDTO.getId())).thenReturn(Optional.of(existingEmployee));
        when(departmentRepository.findById(employeeDTO.getDepartmentId())).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        // Act
        EmployeeDTO result = employeeService.updateEmployee(employeeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(null, result.getDepartmentId());
        // Verify that the repository methods were called
        verify(employeeRepository, times(1)).findById(employeeDTO.getId());
        verify(departmentRepository, times(1)).findById(employeeDTO.getDepartmentId());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_ExistingId_DeletesEmployee() {
        // Arrange
        Long employeeId = 1L;

        // Act
        employeeService.deleteEmployee(employeeId);

        // Verify that the repository method was called
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
