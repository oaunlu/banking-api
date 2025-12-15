package com.banking.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(
        @NotBlank(message = "Source account ID cannot be blank") String fromAccountId,
        @NotBlank(message = "Destination account ID cannot be blank") String toAccountId,
        @NotNull(message = "Amount cannot be null") @Positive(message = "Amount must be greater than zero") BigDecimal amount) {
}
