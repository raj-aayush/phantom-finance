package com.rajaayush.api.controller;

import com.rajaayush.api.dto.CreateAccountRequest;
import com.rajaayush.api.dto.CreateCustomerRequest;
import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Customer;
import com.rajaayush.api.service.AccountService;
import com.rajaayush.api.service.CustomerService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/")
    public Customer createCustomer(@RequestBody CreateCustomerRequest request) throws BadRequestException {
        return customerService.create(request);
    }

    @GetMapping("/{customerId}/")
    public Customer getAllCustomers(@PathVariable UUID customerId) {
        return customerService.getCustomer(customerId);
    }

    @GetMapping("/{customerId}/accounts/")
    public List<Account> getAllAccountsForCustomer(@PathVariable UUID customerId) throws BadRequestException {
        return customerService.getAllAccountsForCustomer(customerId);
    }

    @PostMapping("/{customerId}/accounts/")
    public Account createAccount(@PathVariable UUID customerId, @RequestBody CreateAccountRequest request) throws BadRequestException {
        return accountService.create(customerId, request.getInitialAmount());
    }
}
