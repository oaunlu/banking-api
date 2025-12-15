package com.banking.api.repository;

import com.banking.api.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByFromOrderByTransactionDateDesc(String fromAccountId);

    List<Transaction> findByToOrderByTransactionDateDesc(String toAccountId);
}
