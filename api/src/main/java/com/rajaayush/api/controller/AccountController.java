package com.rajaayush.api.controller;

import com.rajaayush.api.dto.CreateAccountRequest;
import com.rajaayush.api.entity.Account;
import com.rajaayush.api.service.AccountService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
