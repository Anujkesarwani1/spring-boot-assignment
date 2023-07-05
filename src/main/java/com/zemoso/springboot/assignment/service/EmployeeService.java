package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.dto.EmployeeDTO;
import com.zemoso.springboot.assignment.entity.Department;
import com.zemoso.springboot.assignment.entity.Employee;
import com.zemoso.springboot.assignment.repository.DepartmentRepository;
import com.zemoso.springboot.assignment.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDto)
                .toList();
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id " + id));

        return convertToDto(employee);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() ->
                        new NoSuchElementException(("Department not found with id " +
                                employeeDTO.getDepartmentId())));

        Employee employee = convertToEntity(employeeDTO);
        employee.setDepartment(department);

        // Clear the ID to ensure it is generated by the database
        employee.setId(null);

        employee = employeeRepository.save(employee);
        return convertToDto(employee);
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(()
                -> new NoSuchElementException("Employee not found with id " + employeeDTO.getId()));

        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(()
                        -> new NoSuchElementException("Employee not found with id " + employeeDTO.getDepartmentId()));
        existingEmployee.setDepartment(department);

        Employee employee = employeeRepository.save(existingEmployee);
        return convertToDto(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO convertToDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setDepartmentId(employee.getDepartment().getId());

        return employeeDTO;
    }
    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        return employee;
    }

    // For Testing
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Long departmentId = employeeDTO.getDepartmentId();
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);

        if (departmentOptional.isEmpty()) {
            throw new NoSuchElementException("Department not found with id: " + departmentId);
        }

        Department department = departmentOptional.get();
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);

        return new EmployeeDTO(
                savedEmployee.getId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getEmail(),
                departmentId
        );
    }
}
