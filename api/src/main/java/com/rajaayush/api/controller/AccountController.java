package com.rajaayush.api.controller;

import com.rajaayush.api.dto.CreateAccountRequest;
import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Transaction;
import com.rajaayush.api.service.AccountService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/")
    public Account create(@Valid @RequestBody CreateAccountRequest request) throws BadRequestException {
        return accountService.create(request.getCustomerId(), request.getBalance());
    }

    @GetMapping("/{accountId}/")
    public double getBalance(@PathVariable UUID accountId) throws BadRequestException {
        return accountService.getBalance(accountId);
    }

    @GetMapping("/{accountId}/history/")
    public List<Transaction> getTransactionHistory(@PathVariable UUID accountId) throws BadRequestException {
        return accountService.getTransactionHistory(accountId);
    }
}
