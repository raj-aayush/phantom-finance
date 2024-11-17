package com.rajaayush.api.repository;

import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findAllByOwner(Customer customer);
}
