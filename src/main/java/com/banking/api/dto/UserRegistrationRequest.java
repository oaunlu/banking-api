package com.banking.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

public record UserRegistrationRequest(
        @Null(message = "Id must be null for registration")
        String id,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @Null(message = "Created at must be null")
        String createdAt,

        String roles
) {
}
