package com.rajaayush.api.controller;

import com.rajaayush.api.entity.Customer;
import com.rajaayush.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }
}
