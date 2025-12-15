package com.banking.api.dto;

import com.banking.api.entity.Transaction;
import com.banking.api.entity.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String fromAccountId,
        String toAccountId,
        BigDecimal amount,
        LocalDateTime transactionDate,
        TransactionStatus status) {

    public static TransactionResponse fromTransaction(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getFrom(),
                transaction.getTo(),
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getStatus());
    }
}
