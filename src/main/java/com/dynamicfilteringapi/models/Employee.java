package com.dynamicfilteringapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Primeiro nome é obrigatório")
    private String firstName;

    @NotBlank(message = "Último nome é obrigatório")
    private String lastName;

    private String position;
    private Double salary;
    private LocalDate hireDate;
    private String department;
    private Boolean active;
}
