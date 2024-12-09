package com.maveric.csp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maveric.csp.entities.Customer;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.CustomerNotFoundException;
import com.maveric.csp.services.CustomerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CustomerService customerService;

//*********************************Create Customer******************************************************

	@Operation(summary = "API to Create Customer")
	@PostMapping("/save")
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) throws Exception {

		log.info("CustomerController : saveCustomer() : Call Started");
		log.info("RequestBody = " + customer);

		Customer savedCustomer = customerService.saveCustomer(customer);

		log.info("CustomerController : saveCustomer() : Call Ended");
		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);

		
	}

//*********************************Fetch Customer By CustomerID******************************************************	
	@Operation(summary = "API to Find Customer by customerId")
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) throws CustomerNotFoundException {

		log.info("CustomerController : getCustomerById() : Call Started");

		Customer customer = customerService.getCustomerById(customerId);

		log.info("CustomerController : getCustomerById() : Call Ended");

		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	
	//*********************************Fetch Customer By CustomerName******************************************************	
		@Operation(summary = "API to Find Customer by CustomerName")
		@GetMapping("/getByName/{customerName}")
		public ResponseEntity<List<Customer>> getCustomerBycustomerName(@PathVariable String customerName)
				throws CustomerNotFoundException {

			log.info("CustomerController : getCustomerById() : Call Started");
			log.info("CustomerName : {} " , customerName);

			List<Customer> customer = customerService.getCustomerBycustomerName(customerName);

			return new ResponseEntity<>(customer, HttpStatus.OK);

		}

//*********************************Fetch All Customers******************************************************

	@Operation(summary = "Api to Fetch All Customers")
	@GetMapping("/allCustomers")
	public ResponseEntity<List<Customer>> getAllCustomers() throws CustomerNotFoundException {

		  
	            List<Customer> allCustomers = customerService.getAllCustomers();
	            return new ResponseEntity<>(allCustomers, HttpStatus.OK);
	            
	     
	}
	
	
//*********************************Update Customer******************************************************

	@Operation(summary = "API to Update Customer")
	@PutMapping("/update")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws AllExceptions, CustomerNotFoundException {

		log.info("CustomerController : updateCustomer() : Call Started");

		Customer updatedCustomer = customerService.updateCustomer(customer);

		log.info("CustomerController : updateCustomer() : Call Ended");
		return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);

	}

}
