package com.banking.api.dto;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record AccountRequest(
        String name,
        @PositiveOrZero(message = "Initial balance must be zero or positive") BigDecimal balance) {
}
