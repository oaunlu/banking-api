package com.banking.api.dto;

public record AuthRequest(
        String username,
        String password) {
}
