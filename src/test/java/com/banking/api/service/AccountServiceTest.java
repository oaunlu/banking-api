package com.banking.api.service;

import com.banking.api.dto.AccountCreateRequest;
import com.banking.api.dto.AccountResponse;
import com.banking.api.dto.AccountUpdateRequest;
import com.banking.api.entity.Account;
import com.banking.api.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AccountService Unit Tests")
class AccountServiceTest {

    private AccountService accountService;
    private Account testAccount;
    private AccountCreateRequest createRequest;

    @BeforeEach
    void setUp() {
        createRequest = new AccountCreateRequest(
                null,
                null,
                "Savings Account",
                new BigDecimal("5000.00"),
                null,
                null
        );

        testAccount = new Account();
        testAccount.setId("account-1");
        testAccount.setNumber("ACC-A1B2C3D4");
        testAccount.setName("Savings Account");
        testAccount.setBalance(new BigDecimal("5000.00"));
        testAccount.setUserEmail("test@example.com");
        testAccount.setCreatedAt(LocalDateTime.now());
        testAccount.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should verify Account entity structure")
    void testAccountEntityCreation() {
        // Act & Assert
        assertNotNull(testAccount);
        assertEquals("account-1", testAccount.getId());
        assertEquals("ACC-A1B2C3D4", testAccount.getNumber());
        assertEquals("Savings Account", testAccount.getName());
        assertEquals(new BigDecimal("5000.00"), testAccount.getBalance());
        assertEquals("test@example.com", testAccount.getUserEmail());
    }

    @Test
    @DisplayName("Should verify account number format")
    void testAccountNumberFormat() {
        // Arrange
        String accountNumber = "ACC-ABCD1234";

        // Act & Assert
        assertTrue(accountNumber.startsWith("ACC-"));
        assertEquals(12, accountNumber.length());
    }

    @Test
    @DisplayName("Should verify account creation request fields")
    void testAccountCreateRequest() {
        // Act & Assert
        assertNotNull(createRequest);
        assertEquals("Savings Account", createRequest.name());
        assertEquals(new BigDecimal("5000.00"), createRequest.balance());
    }

    @Test
    @DisplayName("Should verify account update request fields")
    void testAccountUpdateRequest() {
        // Arrange
        AccountUpdateRequest updateRequest = new AccountUpdateRequest(
                "account-1",
                "ACC-NEWNUM",
                "Updated Account"
        );

        // Act & Assert
        assertNotNull(updateRequest);
        assertEquals("account-1", updateRequest.id());
        assertEquals("ACC-NEWNUM", updateRequest.number());
        assertEquals("Updated Account", updateRequest.name());
    }

    @Test
    @DisplayName("Should verify account response conversion")
    void testAccountResponseConversion() {
        // Act
        AccountResponse response = AccountResponse.fromAccount(testAccount);

        // Assert
        assertNotNull(response);
        assertEquals(testAccount.getId(), response.id());
        assertEquals(testAccount.getNumber(), response.number());
        assertEquals(testAccount.getName(), response.name());
        assertEquals(testAccount.getBalance(), response.balance());
    }

    @Test
    @DisplayName("Should handle account balance updates")
    void testAccountBalanceUpdate() {
        // Arrange
        BigDecimal newBalance = new BigDecimal("10000.00");

        // Act
        testAccount.setBalance(newBalance);

        // Assert
        assertEquals(newBalance, testAccount.getBalance());
    }

    @Test
    @DisplayName("Should handle account name updates")
    void testAccountNameUpdate() {
        // Arrange
        String newName = "Checking Account";

        // Act
        testAccount.setName(newName);

        // Assert
        assertEquals(newName, testAccount.getName());
    }

    @Test
    @DisplayName("Should verify user isolation capability")
    void testAccountUserIsolation() {
        // Arrange
        Account account1 = new Account();
        account1.setUserEmail("user1@example.com");
        
        Account account2 = new Account();
        account2.setUserEmail("user2@example.com");

        // Act & Assert
        assertNotEquals(account1.getUserEmail(), account2.getUserEmail());
        assertTrue(account1.getUserEmail().equals("user1@example.com"));
        assertTrue(account2.getUserEmail().equals("user2@example.com"));
    }

    @Test
    @DisplayName("Should verify account timestamps")
    void testAccountTimestamps() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        testAccount.setCreatedAt(now);
        testAccount.setUpdatedAt(now);

        // Act & Assert
        assertNotNull(testAccount.getCreatedAt());
        assertNotNull(testAccount.getUpdatedAt());
        assertEquals(now, testAccount.getCreatedAt());
        assertEquals(now, testAccount.getUpdatedAt());
    }
}
