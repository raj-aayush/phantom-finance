package com.rajaayush.api.controller;

import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Customer;
import com.rajaayush.api.service.CustomerService;
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

    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @GetMapping("/{id}/accounts")
    public List<Account> getAllAccountsForCustomer(@PathVariable UUID id) throws BadRequestException {
        return customerService.getAllAccountsForCustomer(id);
    }
}
