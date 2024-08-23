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
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(columnNames = "orderNumber"))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Número do pedido é obrigatório")
    @Column(unique = true)
    private String orderNumber;

    private String customerName;
    private StatusType status;
    private Double totalAmount;

    @Column(nullable = false, updatable = false)
    private LocalDate orderDate = LocalDate.now();
}