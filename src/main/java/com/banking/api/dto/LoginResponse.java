package com.banking.api.dto;

import java.time.LocalDateTime;

public record LoginResponse(
        String accessToken,
        String tokenType,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt) {
}
