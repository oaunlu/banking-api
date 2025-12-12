package com.banking.api.service;

import com.banking.api.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    public void transfer() {
    }

    public List<Transaction> getTransactionHistory(String accountId) {
        return List.of();
    }
}
