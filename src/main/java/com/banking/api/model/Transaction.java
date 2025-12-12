package com.banking.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        Long id,
        Account from,
        Account to,
        BigDecimal amount,
        LocalDateTime transactionDate,
        TransactionStatus status
) {
}
