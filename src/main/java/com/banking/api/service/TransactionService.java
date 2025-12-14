package com.banking.api.service;

import com.banking.api.entity.Transaction;
import com.banking.api.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void transfer() {
        transactionRepository.save(new Transaction(null, null, null, null, null, null));
    }

    public List<Transaction> getTransactionHistory(String accountId) {
        return List.of();
    }
}
