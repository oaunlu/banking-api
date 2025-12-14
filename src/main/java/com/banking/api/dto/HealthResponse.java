package com.banking.api.dto;

import java.time.LocalDateTime;

public record HealthResponse(
        String status,
        String message,
        LocalDateTime timestamp) {
}
