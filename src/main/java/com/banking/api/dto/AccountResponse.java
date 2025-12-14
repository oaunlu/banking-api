package com.banking.api.dto;

import com.banking.api.entity.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
                String id,
                String number,
                String name,
                BigDecimal balance,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        public static AccountResponse fromAccount(Account account) {
                return new AccountResponse(
                                account.getId(),
                                account.getNumber(),
                                account.getName(),
                                account.getBalance(),
                                account.getCreatedAt(),
                                account.getUpdatedAt());
        }
}
