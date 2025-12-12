package com.banking.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Account(
        String id,
        String number,
        String name,
        BigDecimal balance,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
