package com.banking.api.service;

import com.banking.api.dto.AccountCreateRequest;
import com.banking.api.dto.AccountResponse;
import com.banking.api.dto.AccountUpdateRequest;
import com.banking.api.entity.Account;
import com.banking.api.repository.AccountRepository;
import com.banking.api.security.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final SecurityService securityService;

    public AccountService(AccountRepository accountRepository,  SecurityService securityService) {
        this.accountRepository = accountRepository;
        this.securityService = securityService;
    }

    public AccountResponse createAccount(AccountCreateRequest request) {
        Account account = new Account();
        account.setNumber(generateAccountNumber());
        account.setName(request.name());
        account.setBalance(request.balance());
        account.setUserEmail(securityService.getAuthenticatedUsername());
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        Account savedAccount = accountRepository.save(account);
        return AccountResponse.fromAccount(savedAccount);
    }

    public List<AccountResponse> searchAccounts(String number, String name) {
        String userEmail = securityService.getAuthenticatedUsername();
        return accountRepository.getAccountsByNameContaining(name)
                .stream()
                .filter(account -> account.getUserEmail().equals(userEmail))
                .filter(account -> number == null || account.getNumber().contains(number))
                .map(AccountResponse::fromAccount)
                .toList();
    }

    public AccountResponse updateAccount(AccountUpdateRequest request) {
        Account account = accountRepository.getAccountsById(request.id());
        if (account != null) {
            if (request.number() != null) {
                account.setNumber(request.number());
            }
            if (request.name() != null) {
                account.setName(request.name());
            }
            account.setUpdatedAt(LocalDateTime.now());
            Account updatedAccount = accountRepository.save(account);
            return AccountResponse.fromAccount(updatedAccount);
        }
        return null;
    }

    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }

    public AccountResponse getAccountById(String id) {
        Account account = accountRepository.getAccountsById(id);
        return account != null ? AccountResponse.fromAccount(account) : null;
    }

    private String generateAccountNumber() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
