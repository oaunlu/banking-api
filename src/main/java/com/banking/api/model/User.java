package com.banking.api.model;

import java.time.LocalDateTime;

public record User(
        String id,
        String username,
        String password,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
