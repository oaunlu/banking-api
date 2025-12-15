package com.banking.api.controller;

import com.banking.api.dto.TransactionResponse;
import com.banking.api.dto.TransferRequest;
import com.banking.api.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Transaction management endpoints")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Initiate Money Transfer", description = """
            Transfers money from one account to another. Transfers can occur simultaneously.
            Access is restricted to the account owner initiating the transfer.""")
    public TransactionResponse transfer(@Valid @RequestBody TransferRequest request) {
        return transactionService.transfer(request);
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "View Transaction History", description = """
            Retrieves the transaction history for a specified account.
            Access is restricted to the account owner.""")
    public List<TransactionResponse> getTransactionHistory(
            @Parameter(description = "Account ID") @PathVariable String accountId) {
        return transactionService.getTransactionHistory(accountId);
    }
}
