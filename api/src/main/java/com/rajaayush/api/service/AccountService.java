package com.rajaayush.api.service;

import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Customer;
import com.rajaayush.api.entity.Transaction;
import com.rajaayush.api.repository.AccountRepository;
import com.rajaayush.api.repository.CustomerRepository;
import com.rajaayush.api.repository.TransactionRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account create(UUID customerId, double initialAmount) throws IllegalArgumentException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Account ac = new Account();
        ac.owner = customer.get();
        ac.balance = initialAmount;
        return accountRepository.save(ac);
    }

    public double getBalance(UUID accountId) throws IllegalArgumentException {
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return account.get().getBalance();
    }

    public List<Transaction> getTransactionHistory(UUID accountId) throws IllegalArgumentException {
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<Transaction> txns = transactionRepository.findAllBySender(account.get());
        txns.addAll(transactionRepository.findAllByReceiver(account.get()));
        return txns;
    }
}
