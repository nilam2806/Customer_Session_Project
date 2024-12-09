package com.maveric.csp.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maveric.csp.entities.Customer;
import com.maveric.csp.exceptions.CustomerNotFoundException;
import com.maveric.csp.repositories.CustomerRepository;

@Service

public class CustomerServiceImpl implements CustomerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Customer saveCustomer(Customer customer) throws Exception {

		log.info("CustomerServiceImpl : saveCustomer() : Call Started");

		Customer savedCustomer = customerRepository.save(customer);

		if (savedCustomer != null) {
			log.info("CustomerServiceImpl : saveCustomer() : Call Ended");
			return savedCustomer;
		} else {
			 throw new Exception("unable to save customer");
			 
		}

	}

	@Override
	public Customer getCustomerById(Long customerId) throws CustomerNotFoundException {

		log.info("CustomerServiceImpl : getCustomerById() : Call Started");

		Optional<Customer> foundCustomer = customerRepository.findById(customerId);

		if (!foundCustomer.isPresent()) {

			throw new CustomerNotFoundException("Enter the valid customer_Id");

		} else {

			log.info("CustomerServiceImpl : getCustomerById() : Call Ended");
			return foundCustomer.get();
		}

	}
	
	
	@Override
	public List<Customer> getCustomerBycustomerName(String customerName) throws CustomerNotFoundException {
		
		log.info("CustomerServiceImpl : getCustomerBycustomerName() : Call Started");

		List<Customer> foundCustomer = customerRepository.findByCustomerName(customerName);

		if (foundCustomer == null) {

			throw new CustomerNotFoundException("Enter the valid Customer Name");

		} else {

			log.info("CustomerServiceImpl : getCustomerById() : Call Ended");
			return foundCustomer;
		}
		}

	@Override
	public List<Customer> getAllCustomers()throws CustomerNotFoundException {

		log.info("CustomerServiceImpl : getAllCustomer() : Call Started");

		List<Customer> allCustomer = customerRepository.findAll();

		if (allCustomer.size() == 0) {

			log.info("List is empty");

		}
		log.info("CustomerServiceImpl : getAllCustomer() : Call Ended");
		return allCustomer;

	}

	@Override
	public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {

		Customer updatedCustomer = null;
		Optional<Customer> databaseCustomer = customerRepository.findById(customer.getCustomerId());

		if (databaseCustomer.isPresent()) {
			Customer dbCustomer = databaseCustomer.get();
			dbCustomer.setCustomerName(customer.getCustomerName() == null ? dbCustomer.getCustomerName() : customer.getCustomerName());
			dbCustomer.setCustomerEmail(customer.getCustomerEmail() == null ? dbCustomer.getCustomerEmail() : customer.getCustomerEmail());
			dbCustomer.setFinancialGoal(customer.getFinancialGoal() == null ? dbCustomer.getFinancialGoal():customer.getFinancialGoal());
			dbCustomer.setPreferredProduct(customer.getPreferredProduct()== null? dbCustomer.getPreferredProduct() : customer.getPreferredProduct());
			dbCustomer.setWealthMode(customer.getWealthMode() == null? dbCustomer.getWealthMode(): customer.getWealthMode());
			updatedCustomer = customerRepository.save(dbCustomer);
			return updatedCustomer;
		} else {
			
			throw new CustomerNotFoundException("Customer not found");
		}

	}

	
		
}


