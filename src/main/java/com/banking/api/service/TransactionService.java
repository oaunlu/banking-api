package com.banking.api.service;

import com.banking.api.dto.TransactionResponse;
import com.banking.api.dto.TransferRequest;
import com.banking.api.entity.Account;
import com.banking.api.entity.Transaction;
import com.banking.api.entity.TransactionStatus;
import com.banking.api.exception.ResourceNotFoundException;
import com.banking.api.repository.AccountRepository;
import com.banking.api.repository.TransactionRepository;
import com.banking.api.security.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final SecurityService securityService;

    public TransactionService(TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            SecurityService securityService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.securityService = securityService;
    }

    /**
     * Initiates a money transfer between two accounts.
     * Validates both accounts exist, sufficient balance, and user authorization.
     * Transfers can occur simultaneously.
     *
     * @param request Transfer request containing from/to account IDs and amount
     * @return TransactionResponse with transaction details
     * @throws ResourceNotFoundException if accounts don't exist
     * @throws IllegalArgumentException  if account not found or insufficient
     *                                   balance
     * @throws IllegalStateException     if user not authorized to transfer from
     *                                   source account
     */
    public TransactionResponse transfer(TransferRequest request) {
        // Validate the source account exists and belongs to the authenticated user
        Account fromAccount = accountRepository.getAccountsById(request.fromAccountId());
        if (fromAccount == null) {
            throw new ResourceNotFoundException("Source account not found: " + request.fromAccountId());
        }

        String authenticatedUsername = securityService.getAuthenticatedUsername();
        if (!fromAccount.getUserEmail().equals(authenticatedUsername)) {
            throw new IllegalStateException("User not authorized to transfer from this account");
        }

        // Validate destination account exists
        Account toAccount = accountRepository.getAccountsById(request.toAccountId());
        if (toAccount == null) {
            throw new ResourceNotFoundException("Destination account not found: " + request.toAccountId());
        }

        // Validate sufficient balance
        if (fromAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance. Available: " + fromAccount.getBalance() +
                    ", Requested: " + request.amount());
        }

        // Prevent self-transfer
        if (request.fromAccountId().equals(request.toAccountId())) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        // Perform the transfer
        try {
            // Deduct from source account
            fromAccount.setBalance(fromAccount.getBalance().subtract(request.amount()));
            fromAccount.setUpdatedAt(LocalDateTime.now());
            accountRepository.save(fromAccount);

            // Add to destination account
            toAccount.setBalance(toAccount.getBalance().add(request.amount()));
            toAccount.setUpdatedAt(LocalDateTime.now());
            accountRepository.save(toAccount);

            // Record the transaction
            Transaction transaction = new Transaction(
                    request.fromAccountId(),
                    request.toAccountId(),
                    request.amount(),
                    LocalDateTime.now(),
                    TransactionStatus.SUCCESS);
            Transaction savedTransaction = transactionRepository.save(transaction);
            return TransactionResponse.fromTransaction(savedTransaction);
        } catch (Exception e) {
            // Record failed transaction
            Transaction failedTransaction = new Transaction(
                    request.fromAccountId(),
                    request.toAccountId(),
                    request.amount(),
                    LocalDateTime.now(),
                    TransactionStatus.FAILED);
            transactionRepository.save(failedTransaction);
            throw e;
        }
    }

    /**
     * Retrieves the transaction history for a specified account.
     * Access is restricted to the account owner.
     *
     * @param accountId The account ID to retrieve transaction history for
     * @return List of TransactionResponse objects sorted by date (newest first)
     * @throws ResourceNotFoundException if account doesn't exist or user is not
     *                                   authorized
     */
    public List<TransactionResponse> getTransactionHistory(String accountId) {
        Account account = accountRepository.getAccountsById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found: " + accountId);
        }

        String authenticatedUsername = securityService.getAuthenticatedUsername();
        if (!account.getUserEmail().equals(authenticatedUsername)) {
            throw new IllegalStateException("User not authorized to view this account's transaction history");
        }

        // Get all transactions where account is source or destination
        List<Transaction> fromTransactions = transactionRepository.findByFromOrderByTransactionDateDesc(accountId);
        List<Transaction> toTransactions = transactionRepository.findByToOrderByTransactionDateDesc(accountId);

        // Combine and sort by date
        List<Transaction> allTransactions = new java.util.ArrayList<>(fromTransactions);
        allTransactions.addAll(toTransactions);
        allTransactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));

        return allTransactions.stream()
                .map(TransactionResponse::fromTransaction)
                .toList();
    }
}
