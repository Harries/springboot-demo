package com.et.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeID;

    private String firstName;
    private String lastName;
    private Date birthDate;
    private Date hireDate;
    private String position;


}