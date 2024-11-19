package com.rajaayush.api.service;

import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Transaction;
import com.rajaayush.api.repository.AccountRepository;
import com.rajaayush.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Transaction transfer(String senderId, String receiverId, double amount) throws IllegalArgumentException {
        Optional<Account> senderQuery = accountRepository.findById(UUID.fromString(senderId));
        Optional<Account> receiverQuery = accountRepository.findById(UUID.fromString(receiverId));
        if(senderQuery.isEmpty() || receiverQuery.isEmpty()) {
            throw new IllegalArgumentException("Valid sender and receiver IDs must be provided");
        }
        Account sender = senderQuery.get();
        Account receiver = receiverQuery.get();
        if(amount <= 0) {
            throw new IllegalArgumentException("Please enter a valid amount");
        }
        if(sender.getBalance() < amount) {
            throw new IllegalArgumentException("Account has insufficient funds");
        }
        if(sender == receiver) {
            throw new IllegalArgumentException("Sender and receiver accounts must be different");
        }

        Transaction txn = new Transaction();
        sender.setBalance(sender.getBalance()-amount);
        txn.setSender(sender);
        accountRepository.save(sender);

        receiver.setBalance(receiver.getBalance() + amount);
        txn.setReceiver(receiver);
        txn.setAmount(amount);
        return transactionRepository.save(txn);
    }
}
