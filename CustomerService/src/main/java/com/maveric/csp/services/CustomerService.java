package com.maveric.csp.services;

import java.util.List;

import com.maveric.csp.entities.Customer;
import com.maveric.csp.exceptions.CustomerNotFoundException;

public interface CustomerService {

	public Customer saveCustomer(Customer customer) throws Exception;

	public Customer getCustomerById(Long customerId) throws CustomerNotFoundException;

	public List<Customer> getAllCustomers() throws CustomerNotFoundException;

	public Customer updateCustomer(Customer customer) throws CustomerNotFoundException;

	public List<Customer> getCustomerBycustomerName(String customerName) throws CustomerNotFoundException;

}
