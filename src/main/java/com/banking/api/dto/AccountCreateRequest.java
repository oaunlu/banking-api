package com.banking.api.dto;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record AccountCreateRequest(
                @Null(message = "Id must be null for account creation") String id,

                String number,

                String name,

                @PositiveOrZero(message = "Initial balance must be zero or positive") BigDecimal balance,

                @Null(message = "Created at must be null") String createdAt,

                @Null(message = "Updated at must be null") String updatedAt) {
}
