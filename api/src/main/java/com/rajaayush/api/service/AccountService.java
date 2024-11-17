package com.rajaayush.api.service;

import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Customer;
import com.rajaayush.api.repository.AccountRepository;
import com.rajaayush.api.repository.CustomerRepository;
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

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account create(UUID customerId, double initialAmount) throws BadRequestException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()) {
            throw new BadRequestException();
        }
        Account ac = new Account();
        ac.owner = customer.get();
        ac.balance = initialAmount;
        return accountRepository.save(ac);
    }
}
