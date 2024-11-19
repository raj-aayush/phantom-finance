package com.rajaayush.api.controller;

import com.rajaayush.api.dto.CreateAccountRequest;
import com.rajaayush.api.dto.CreateCustomerRequest;
import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Customer;
import com.rajaayush.api.service.AccountService;
import com.rajaayush.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching customers.");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerRequest request) {
        try {
            Customer customer = customerService.create(request);
            return ResponseEntity.ok(customer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid customer data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while creating the customer.");
        }
    }

    @GetMapping("/{customerId}/")
    public ResponseEntity<?> getCustomer(@PathVariable UUID customerId) {
        try {
            Customer customer = customerService.getCustomer(customerId);
            return ResponseEntity.ok(customer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid customer ID: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching the customer.");
        }
    }

    @GetMapping("/{customerId}/accounts/")
    public ResponseEntity<?> getAllAccountsForCustomer(@PathVariable UUID customerId) {
        try {
            List<Account> accounts = customerService.getAllAccountsForCustomer(customerId);
            return ResponseEntity.ok(accounts);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid customer ID: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching accounts.");
        }
    }

    @PostMapping("/{customerId}/accounts/")
    public ResponseEntity<?> createAccount(@PathVariable UUID customerId, @RequestBody CreateAccountRequest request) {
        try {
            Account account = accountService.create(customerId, request.getInitialAmount());
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid account data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while creating the account.");
        }
    }
}
