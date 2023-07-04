package com.zemoso.springboot.assignment.repository;

import com.zemoso.springboot.assignment.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findDepartmentById(Long departmentId);
}
