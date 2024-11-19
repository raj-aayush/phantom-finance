package com.rajaayush.api.controller;

import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Transaction;
import com.rajaayush.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public ResponseEntity<?> getAllAccounts() {
        try {
            List<Account> accounts = accountService.getAllAccounts();
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching accounts.");
        }
    }

    @GetMapping("/{accountId}/")
    public ResponseEntity<?> getBalance(@PathVariable UUID accountId) {
        try {
            double balance = accountService.getBalance(accountId);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid account ID: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching the account balance.");
        }
    }

    @GetMapping("/{accountId}/history/")
    public ResponseEntity<?> getTransactionHistory(@PathVariable UUID accountId) {
        try {
            List<Transaction> transactions = accountService.getTransactionHistory(accountId);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid account ID: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching transaction history.");
        }
    }
}
