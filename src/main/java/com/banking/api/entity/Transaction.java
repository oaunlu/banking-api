package com.banking.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "from_id")
    String from;
    @Column(name = "to_id")
    String to;
    @Column(name = "amount")
    BigDecimal amount;
    @Column(name = "transaction_date")
    LocalDateTime transactionDate;
    @Column(name = "status")
    TransactionStatus status;

    public Transaction(@NotBlank(message = "Source account ID cannot be blank") String fromId,
                       @NotBlank(message = "Destination account ID cannot be blank") String toId,
                       @NotNull(message = "Amount cannot be null") @Positive(message = "Amount must be greater than zero") BigDecimal amount,
                       LocalDateTime now, TransactionStatus transactionStatus) {
        this.from = fromId;
        this.to = toId;
        this.amount = amount;
        this.transactionDate = now;
        this.status = transactionStatus;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
