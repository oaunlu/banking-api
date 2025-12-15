package com.banking.api.controller;

import com.banking.api.dto.AccountRequest;
import com.banking.api.dto.AccountResponse;
import com.banking.api.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Account management endpoints")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Operation(summary = "Create Account", description = "Creates a new bank account for the authenticated user")
    public AccountResponse createAccount(@Valid @RequestBody AccountRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping
    @Operation(summary = "Search Accounts", description = "Search accounts for the authenticated user. Filterable by account number and name")
    public List<AccountResponse> searchAccounts(
            @Parameter(description = "Account number filter") @RequestParam(required = false) String number,
            @Parameter(description = "Account name filter") @RequestParam(required = false) String name) {
        return accountService.searchAccounts(number, name);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Account by ID", description = "Retrieves a specific account by its ID")
    public AccountResponse getAccountById(
            @Parameter(description = "Account ID") @PathVariable String id) {
        return accountService.getAccountById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Account", description = "Updates an existing account")
    public AccountResponse updateAccount(@Parameter(description = "Account ID") @PathVariable String id,
                                         @Valid @RequestBody AccountRequest request) {
        return accountService.updateAccount(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Account", description = "Deletes an account by its ID")
    public void deleteAccount(
            @Parameter(description = "Account ID") @PathVariable String id) {
        accountService.deleteAccount(id);
    }
}
