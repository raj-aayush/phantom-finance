package com.rajaayush.api.controller;

import com.rajaayush.api.dto.CreateTransactionRequest;
import com.rajaayush.api.entity.Transaction;
import com.rajaayush.api.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity<?> createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        try {
            Transaction transaction = transactionService.transfer(request.getSender(), request.getReceiver(), request.getAmount());
            return ResponseEntity.ok(transaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid transaction request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred.");
        }
    }
}
