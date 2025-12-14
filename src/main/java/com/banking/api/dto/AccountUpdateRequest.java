package com.banking.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountUpdateRequest(
        @NotBlank(message = "Account id is required")
        String id,

        String number,

        String name
) {
}
