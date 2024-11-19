package com.rajaayush.api.service;

import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Customer;
import com.rajaayush.api.entity.Transaction;
import com.rajaayush.api.repository.AccountRepository;
import com.rajaayush.api.repository.CustomerRepository;
import com.rajaayush.api.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    private UUID customerId;
    private UUID accountId;
    private Customer customer;
    private Account account;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        accountId = UUID.randomUUID();

        customer = new Customer();
        customer.setId(customerId);
        customer.setFirstName("John");
        customer.setEmail("john@example.com");

        account = new Account();
        account.setId(accountId);
        account.setOwner(customer);
        account.setBalance(1000);
    }

    @Test
    void testGetAllAccounts() {
        List<Account> accounts = List.of(account);
        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.getAllAccounts();

        assertEquals(1, result.size());
        assertEquals(account, result.getFirst());
        verify(accountRepository).findAll();
    }

    @Test
    void testCreateAccount_Success() {
        double initialAmount = 500;

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.create(customerId, initialAmount);

        assertNotNull(result);
        assertEquals(customer, result.getOwner());
        assertEquals(initialAmount, result.getBalance());

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();
        assertEquals(customer, savedAccount.getOwner());
        assertEquals(initialAmount, savedAccount.getBalance());
    }


    @Test
    void testCreateAccount_CustomerNotFound() {
        double initialAmount = 500;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.create(customerId, initialAmount);
        });
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testGetBalance_Success() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        double balance = accountService.getBalance(accountId);

        assertEquals(account.getBalance(), balance);
        verify(accountRepository).findById(accountId);
    }

    @Test
    void testGetBalance_AccountNotFound() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.getBalance(accountId);
        });
        verify(accountRepository).findById(accountId);
    }

    @Test
    void testGetTransactionHistory_Success() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        List<Transaction> sentTransactions = new ArrayList<>(List.of(new Transaction()));
        List<Transaction> receivedTransactions = new ArrayList<>(List.of(new Transaction()));

        when(transactionRepository.findAllBySender(account)).thenReturn(sentTransactions);
        when(transactionRepository.findAllByReceiver(account)).thenReturn(receivedTransactions);

        List<Transaction> result = accountService.getTransactionHistory(accountId);

        assertEquals(2, result.size());
        assertTrue(result.containsAll(sentTransactions));
        assertTrue(result.containsAll(receivedTransactions));

        verify(transactionRepository).findAllBySender(account);
        verify(transactionRepository).findAllByReceiver(account);
    }

    @Test
    void testGetTransactionHistory_AccountNotFound() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.getTransactionHistory(accountId);
        });

        verify(transactionRepository, never()).findAllBySender(any(Account.class));
        verify(transactionRepository, never()).findAllByReceiver(any(Account.class));
    }
}
