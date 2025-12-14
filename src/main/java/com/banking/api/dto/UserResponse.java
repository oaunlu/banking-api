package com.banking.api.dto;

import java.time.LocalDateTime;

public record UserResponse(
        String id,
        String username,
        String email,
        LocalDateTime createdAt,
        String roles
) {
}
