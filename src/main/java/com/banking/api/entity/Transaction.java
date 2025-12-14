package com.banking.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public record Transaction(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        Long id,
        @Column(name = "from_id")
        String from,
        @Column(name = "to_id")
        String to,
        @Column(name = "amount")
        BigDecimal amount,
        @Column(name = "transaction_date")
        LocalDateTime transactionDate,
        @Column(name = "status")
        TransactionStatus status
) {
}
