package com.rajaayush.api.service;

import com.rajaayush.api.dto.CreateCustomerRequest;
import com.rajaayush.api.entity.Account;
import com.rajaayush.api.entity.Customer;
import com.rajaayush.api.repository.AccountRepository;
import com.rajaayush.api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        customer = new Customer();
        customer.setId(customerId);
        customer.setFirstName("John");
        customer.setEmail("john@example.com");
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = List.of(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals(customer, result.getFirst());
        verify(customerRepository).findAll();
    }

    @Test
    void testCreateCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFirstName("Jane");
        request.setEmail("jane@example.com");
        request.setInitialAmount(1000);

        Customer savedCustomer = new Customer();
        savedCustomer.setId(UUID.randomUUID());
        savedCustomer.setFirstName("Jane");
        savedCustomer.setEmail("jane@example.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        Customer result = customerService.create(request);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("jane@example.com", result.getEmail());

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerCaptor.capture());
        Customer capturedCustomer = customerCaptor.getValue();
        assertEquals("Jane", capturedCustomer.getFirstName());
        assertEquals("jane@example.com", capturedCustomer.getEmail());

        verify(accountService).create(savedCustomer.getId(), request.getInitialAmount());
    }

    @Test
    void testGetCustomer_Success() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomer(customerId);

        assertNotNull(result);
        assertEquals(customer, result);
        verify(customerRepository).findById(customerId);
    }

    @Test
    void testGetCustomer_NotFound() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(Error.class, () -> {
            customerService.getCustomer(customerId);
        });
        verify(customerRepository).findById(customerId);
    }

    @Test
    void testGetAllAccountsForCustomer_Success() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        List<Account> accounts = List.of(new Account());
        when(accountRepository.findAllByOwner(customer)).thenReturn(accounts);

        List<Account> result = customerService.getAllAccountsForCustomer(customerId);

        assertNotNull(result);
        assertEquals(accounts, result);
        verify(customerRepository).findById(customerId);
        verify(accountRepository).findAllByOwner(customer);
    }

    @Test
    void testGetAllAccountsForCustomer_CustomerNotFound() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            customerService.getAllAccountsForCustomer(customerId);
        });
        verify(customerRepository).findById(customerId);
        verify(accountRepository, never()).findAllByOwner(any(Customer.class));
    }
}
