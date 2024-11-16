package com.rajaayush.api.service;

import com.rajaayush.api.entity.Customer;
import com.rajaayush.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }
}
