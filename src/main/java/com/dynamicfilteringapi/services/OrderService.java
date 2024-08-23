package com.dynamicfilteringapi.services;

import com.dynamicfilteringapi.exceptions.DuplicateFieldException;
import com.dynamicfilteringapi.exceptions.EntityNotFoundException;
import com.dynamicfilteringapi.exceptions.EntityActionException;
import com.dynamicfilteringapi.models.Order;
import com.dynamicfilteringapi.models.StatusType;
import com.dynamicfilteringapi.repositories.OrderRepository;
import com.dynamicfilteringapi.specifications.OrderSpecification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> filterOrders(
            String orderNumber,
            String customerName,
            String status,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (status != null && !isValidStatus(status))  throw new EntityActionException("Order", "filtrar", "Status inválido: " + status);
        return orderRepository.findAll(OrderSpecification.filterOrders(orderNumber, customerName, status, minAmount, maxAmount, startDate, endDate));
    }

    private boolean isValidStatus(String status) {
        try {
            StatusType.valueOf(status);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order", id));
    }

    public Order saveOrder(Order order) {
        try {
            if (order.getStatus() == null) {
                throw new EntityActionException("Order", "salvar", "Status do pedido não pode ser nulo");
            }
            return orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateFieldException("Order", "orderNumber", order.getOrderNumber());
        }
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(existingOrder -> {
            if (updatedOrder.getStatus() != null) {
                existingOrder.setStatus(updatedOrder.getStatus());
            }
            existingOrder.setOrderNumber(updatedOrder.getOrderNumber());
            existingOrder.setCustomerName(updatedOrder.getCustomerName());
            existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
            return orderRepository.save(existingOrder);
        }).orElseThrow(() -> new EntityNotFoundException("Order", id));
    }

    public boolean deleteOrder(Long id) {
        return orderRepository.findById(id).map(order -> {
            orderRepository.delete(order);
            return true;
        }).orElseThrow(() -> new EntityNotFoundException("Order", id));
    }
}
