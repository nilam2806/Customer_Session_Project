package com.maveric.csp.customer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.maveric.csp.controller.CustomerController;
import com.maveric.csp.entities.Customer;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.CustomerNotFoundException;
import com.maveric.csp.services.CustomerService;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
 class CustomerServiceApplicationTests {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        // Initialize a test customer object
        testCustomer = new Customer();
        testCustomer.setCustomerId(1L);
        testCustomer.setCustomerName("Test Customer");
        // Set other properties as needed
    }

    @Test
    void testSaveCustomer() throws Exception {
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(testCustomer);

        ResponseEntity<Customer> responseEntity = customerController.saveCustomer(testCustomer);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(testCustomer, responseEntity.getBody());
    }

    @Test
    void testGetCustomerById() throws CustomerNotFoundException {
        when(customerService.getCustomerById(1L)).thenReturn(testCustomer);

        ResponseEntity<Customer> responseEntity = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testCustomer, responseEntity.getBody());
    }

    @Test
    void testGetCustomerBycustomerName() throws CustomerNotFoundException {
        List<Customer> testCustomers = new ArrayList<>();
        testCustomers.add(testCustomer);

        when(customerService.getCustomerBycustomerName("Test Customer")).thenReturn(testCustomers);

        ResponseEntity<List<Customer>> responseEntity = customerController.getCustomerBycustomerName("Test Customer");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testCustomers, responseEntity.getBody());
    }

    @Test
    void testGetAllCustomers() throws CustomerNotFoundException {
        List<Customer> testCustomers = new ArrayList<>();
        testCustomers.add(testCustomer);

        when(customerService.getAllCustomers()).thenReturn(testCustomers);

        ResponseEntity<List<Customer>> responseEntity = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testCustomers, responseEntity.getBody());
    }

    @Test
    void testUpdateCustomer() throws CustomerNotFoundException, AllExceptions {
        when(customerService.updateCustomer(any(Customer.class))).thenReturn(testCustomer);

        ResponseEntity<Customer> responseEntity = customerController.updateCustomer(testCustomer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testCustomer, responseEntity.getBody());
    }
}

