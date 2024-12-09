package com.maveric.csp.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.maveric.csp.entities.Customer;
import com.maveric.csp.exceptions.CustomerNotFoundException;
import com.maveric.csp.repositories.CustomerRepository;
import com.maveric.csp.services.CustomerServiceImpl;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveCustomer_Success() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("John Doe");

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.saveCustomer(customer);

        assertEquals(customer, savedCustomer);
    }

    @Test
    public void testGetCustomerById_Success() throws CustomerNotFoundException {
        long customerId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName("John Doe");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer foundCustomer = customerService.getCustomerById(customerId);

        assertEquals(customer, foundCustomer);
    }

    @Test
    public void testGetCustomerById_CustomerNotFoundException() {
        long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(customerId);
        });
    }

    @Test
    public void testGetCustomerByCustomerName_Success() throws CustomerNotFoundException {
        String customerName = "John Doe";
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName(customerName);
        customers.add(customer);

        when(customerRepository.findByCustomerName(customerName)).thenReturn(customers);

        List<Customer> foundCustomers = customerService.getCustomerBycustomerName(customerName);

        assertEquals(customers, foundCustomers);
    }


    @Test
    public void testUpdateCustomer_Success() throws CustomerNotFoundException {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("John Doe");

        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer updatedCustomer = customerService.updateCustomer(customer);

        assertEquals(customer, updatedCustomer);
    }

    @Test
    public void testUpdateCustomer_CustomerNotFoundException() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("John Doe");

        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(customer);
        });
    }
}
