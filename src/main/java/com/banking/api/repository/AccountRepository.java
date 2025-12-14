package com.banking.api.repository;

import com.banking.api.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account,String> {
    Account getAccountsById(String id);

    List<Account> getAccountsByNameContaining(String name);
}
