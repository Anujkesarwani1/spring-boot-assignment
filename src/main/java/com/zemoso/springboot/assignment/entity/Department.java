package com.zemoso.springboot.assignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @Column(name = "department_address", nullable = false)
    private String departmentAddress;

    @Column(name = "department_code", nullable = false)
    private String departmentCode;
}
