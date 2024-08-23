package com.dynamicfilteringapi.specifications;

import com.dynamicfilteringapi.models.Order;
import com.dynamicfilteringapi.models.StatusType;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> filterOrders(
            String orderNumber,
            String customer,
            String status,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (orderNumber != null && !orderNumber.isEmpty()) predicates.add(criteriaBuilder.equal(root.get("orderNumber"), orderNumber));
            if (customer != null && !customer.isEmpty()) predicates.add(criteriaBuilder.like(root.get("customerName"), "%" + customer + "%"));
            if (status != null && !status.isEmpty()) {
                StatusType statusType = StatusType.valueOf(status.toUpperCase());
                predicates.add(criteriaBuilder.equal(root.get("status"), statusType));
            }
            if (minAmount != null) predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("totalAmount"), minAmount));
            if (maxAmount != null) predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("totalAmount"), maxAmount));
            if (startDate != null) predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate"), startDate));
            if (endDate != null) predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("orderDate"), endDate));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
