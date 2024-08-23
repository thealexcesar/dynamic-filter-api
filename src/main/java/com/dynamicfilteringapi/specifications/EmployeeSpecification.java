package com.dynamicfilteringapi.specifications;

import com.dynamicfilteringapi.models.Employee;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> filterEmployees(
            String firstName,
            String department,
            BigDecimal minSalary,
            LocalDate hiredAfter,
            LocalDate hiredBefore,
            Boolean active
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (firstName != null && !firstName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%"));
            }
            if (department != null && !department.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("department"), department));
            }
            if (minSalary != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), minSalary));
            }
            if (hiredAfter != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("hireDate"), hiredAfter));
            }
            if (hiredBefore != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("hireDate"), hiredBefore));
            }
            if (active != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), active));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
