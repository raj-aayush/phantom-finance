package com.rajaayush.api.controller;

import com.rajaayush.api.dto.CreateTransactionRequest;
import com.rajaayush.api.entity.Transaction;
import com.rajaayush.api.service.TransactionService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Transaction createTransaction(@Valid @RequestBody CreateTransactionRequest request) throws BadRequestException {
        return transactionService.transfer(request.getSender(), request.getReceiver(), request.getAmount());
    }
}
