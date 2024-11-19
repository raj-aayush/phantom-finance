package com.rajaayush.api.service;

import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Transaction;
import com.rajaayush.api.repository.AccountRepository;
import com.rajaayush.api.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Account sender;
    private Account receiver;
    private UUID senderId;
    private UUID receiverId;

    @BeforeEach
    public void setUp() {
        senderId = UUID.randomUUID();
        receiverId = UUID.randomUUID();

        sender = new Account();
        sender.setId(senderId);
        sender.setBalance(1000);

        receiver = new Account();
        receiver.setId(receiverId);
        receiver.setBalance(500);
    }

    @Test
    public void testSuccessfulTransfer() {
        double amount = 200;

        when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction txn = transactionService.transfer(senderId.toString(), receiverId.toString(), amount);

        assertNotNull(txn);
        assertEquals(sender, txn.getSender());
        assertEquals(receiver, txn.getReceiver());
        assertEquals(amount, txn.getAmount());

        assertEquals(800, sender.getBalance());
        assertEquals(700, receiver.getBalance());

        verify(accountRepository).save(sender);
        verify(transactionRepository).save(txn);
    }

    @Test
    public void testSenderNotFound() {
        when(accountRepository.findById(senderId)).thenReturn(Optional.empty());
        when(accountRepository.findById(receiverId)).thenReturn(Optional.of(receiver));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transfer(senderId.toString(), receiverId.toString(), 200);
        });

        assertEquals("Valid sender and receiver IDs must be provided", exception.getMessage());
    }

    @Test
    public void testReceiverNotFound() {
        when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(receiverId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transfer(senderId.toString(), receiverId.toString(), 200);
        });

        assertEquals("Valid sender and receiver IDs must be provided", exception.getMessage());
    }

    @Test
    public void testInvalidAmount() {
        when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(receiverId)).thenReturn(Optional.of(receiver));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transfer(senderId.toString(), receiverId.toString(), -100);
        });

        assertEquals("Please enter a valid amount", exception.getMessage());
    }

    @Test
    public void testInsufficientFunds() {
        when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(receiverId)).thenReturn(Optional.of(receiver));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transfer(senderId.toString(), receiverId.toString(), 1500);
        });

        assertEquals("Account has insufficient funds", exception.getMessage());
    }

    @Test
    public void testSameSenderAndReceiver() {
        when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transfer(senderId.toString(), senderId.toString(), 100);
        });

        assertEquals("Sender and receiver accounts must be different", exception.getMessage());
    }
}
