package com.rajaayush.api.repository;

import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllBySender(Account sender);

    List<Transaction> findAllByReceiver(Account receiver);
};
