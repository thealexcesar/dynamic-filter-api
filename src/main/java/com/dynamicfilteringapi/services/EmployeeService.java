package com.dynamicfilteringapi.services;

import com.dynamicfilteringapi.exceptions.DuplicateFieldException;
import com.dynamicfilteringapi.exceptions.EntityNotFoundException;
import com.dynamicfilteringapi.models.Employee;
import com.dynamicfilteringapi.repositories.EmployeeRepository;
import com.dynamicfilteringapi.specifications.EmployeeSpecification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> filterEmployees(
            String firstName,
            String department,
            BigDecimal minSalary,
            LocalDate hiredAfter,
            LocalDate hiredBefore,
            Boolean active
    ) {
        return employeeRepository.findAll(EmployeeSpecification.filterEmployees(firstName, department, minSalary, hiredAfter, hiredBefore, active));
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empregado", id));
    }

    public Employee saveEmployee(Employee employee) {
        try {
            return employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateFieldException("Empregado", "Primeiro e último nome", employee.getFirstName() + " " + employee.getLastName());
        }
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setFirstName(updatedEmployee.getFirstName());
                    existingEmployee.setLastName(updatedEmployee.getLastName());
                    existingEmployee.setPosition(updatedEmployee.getPosition());
                    existingEmployee.setSalary(updatedEmployee.getSalary());
                    existingEmployee.setHireDate(updatedEmployee.getHireDate());
                    existingEmployee.setDepartment(updatedEmployee.getDepartment());
                    existingEmployee.setActive(updatedEmployee.getActive());
                    return employeeRepository.save(existingEmployee);
                })
                .orElseThrow(() -> new EntityNotFoundException("Empregado", id));
    }

    public boolean deleteEmployee(Long id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employeeRepository.delete(employee);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Empregado", id));
    }
}
