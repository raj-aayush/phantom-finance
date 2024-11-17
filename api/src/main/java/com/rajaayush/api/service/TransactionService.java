package com.rajaayush.api.service;

import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Transaction;
import com.rajaayush.api.repository.AccountRepository;
import com.rajaayush.api.repository.TransactionRepository;
import org.apache.coyote.BadRequestException;
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

    public Transaction transfer(UUID senderId, UUID receiverId, double amount) throws BadRequestException {
        Optional<Account> sender = accountRepository.findById(senderId);
        Optional<Account> receiver = accountRepository.findById(receiverId);
        if(sender.isEmpty() && receiver.isEmpty()) {
            throw new BadRequestException("Either a sender or a receiver ID must be provided");
        }
        Transaction txn = new Transaction();
        if(sender.isPresent()) {
            Account senderAc = sender.get();
            if(senderAc.getBalance() < amount) {
                throw new BadRequestException("Account has insufficient funds");
            }
            senderAc.setBalance(senderAc.getBalance()-amount);
            txn.setSender(senderAc);
            accountRepository.save(senderAc);
        }
        if(receiver.isPresent()) {
            Account receiverAc = receiver.get();
            receiverAc.setBalance(receiverAc.getBalance() + amount);
            txn.setReceiver(receiverAc);
        }
        txn.setAmount(amount);
        return transactionRepository.save(txn);
    }
}
