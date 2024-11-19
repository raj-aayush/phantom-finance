package com.rajaayush.api.service;

import com.rajaayush.api.dto.CreateCustomerRequest;
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
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer create(CreateCustomerRequest request) throws IllegalArgumentException {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setEmail(request.getEmail());
        customer = customerRepository.save(customer);
        accountService.create(customer.getId(), request.getInitialAmount());
        return customer;
    }

    public Customer getCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()) {
            throw new Error("Not Found");
        }
        return customer.get();
    }

    public List<Account> getAllAccountsForCustomer(UUID customerId) throws IllegalArgumentException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return accountRepository.findAllByOwner(customer.get());
    }
}
