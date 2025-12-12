package com.banking.api.controller;

import com.banking.api.model.Transaction;
import com.banking.api.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public void transfer(){
        transactionService.transfer();
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionHistory(@PathVariable String accountId){
        return transactionService.getTransactionHistory(accountId);
    }
}
