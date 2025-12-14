package com.banking.api.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        String error,
        LocalDateTime timestamp) {
}
