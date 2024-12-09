package com.maveric.csp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maveric.csp.entities.Customer;
import com.maveric.csp.entities.CustomerGroup;
import com.maveric.csp.entities.CustomerGroupReference;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.DuplicateNameException;
import com.maveric.csp.repositories.CustomerGroupReferenceRepository;
import com.maveric.csp.repositories.CustomerGroupRepository;
import com.maveric.csp.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerGroupServiceImpl implements CustomerGroupService {

	@Autowired
	CustomerGroupRepository customerGroupRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerGroupReferenceRepository referenceRepository;

	/*
	 * *************************************** createCustomerGroup
	 * *************************************************
	 */
	@Override
	public CustomerGroup createCustomerGroup(CustomerGroup group) throws DuplicateNameException {

		Optional<CustomerGroup> dbGroup = customerGroupRepository.findByGroupName(group.getGroupName());

		System.out.println(dbGroup);

		if (dbGroup.isPresent()) {

			throw new DuplicateNameException("Group name is Duplicate");

		} else {
			CustomerGroup savedGroup = customerGroupRepository.save(group);
			List<Customer> customers = group.getCustomers();
			if (customers != null) {
				for (Customer customer : customers) {

					CustomerGroupReference reference = new CustomerGroupReference();
					Optional<Customer> foundCustomer = customerRepository.findById(customer.getCustomerId());
					if (foundCustomer.isPresent()) {

						Customer dbCustomer = foundCustomer.get();
						reference.setCustomerId(dbCustomer.getCustomerId());
					}
					reference.setGroupId(savedGroup.getGroupId());
					// reference.setCustomerId(0);

					referenceRepository.save(reference);
				}
			}
			return savedGroup;
		}
	}

	/*
	 * *************************************** getCustomerByGroupName
	 * *************************************************
	 */

	@Override
	public List<CustomerGroup> getCustomerByGroupName(String groupName) {

		CustomerGroup customerGroup = new CustomerGroup();

		List<CustomerGroup> customerGroups = new ArrayList<>();
		List<Customer> customers = new ArrayList<>();

		Optional<CustomerGroup> foundGroup = customerGroupRepository.findByGroupName(groupName);

		if (foundGroup.isPresent()) {

			CustomerGroup group = foundGroup.get();

			List<CustomerGroupReference> reference = referenceRepository.findByGroupId(group.getGroupId());

			for (CustomerGroupReference cref : reference) {
				Optional<Customer> customer = customerRepository.findById(cref.getCustomerId());

				if (customer.isPresent()) {
					Customer cust = customer.get();
					customers.add(cust);
				}
			}

			customerGroup.setGroupName(groupName);
			customerGroup.setCustomers(customers);
			customerGroup.setGroupId(group.getGroupId());

			customerGroups.add(customerGroup);

		}

		return customerGroups;
	}

	/*
	 * *************************************** deleteCustomerGroup
	 * *************************************************
	 */

	@Override
	public String deleteCustomerGroup(int groupId) throws AllExceptions {

		Optional<CustomerGroup> foundGroup = customerGroupRepository.findById(groupId);

		if (foundGroup.isPresent()) {
			customerGroupRepository.deleteById(groupId);

			List<CustomerGroupReference> groupReferences = referenceRepository.findByGroupId(groupId);
			referenceRepository.deleteAll(groupReferences);

			return "Success";
		} else {
			throw new AllExceptions("CustomerGroup is not Found");
		}
	}

	/*
	 * *************************************** getAllCustomerGroup
	 * *************************************************
	 */

	@Override
	public List<CustomerGroup> getAllCustomerGroup() {

		List<CustomerGroup> list = customerGroupRepository.findAll();

		return list;
	}

	
	@Transactional
	public CustomerGroup updateCustomerGroup(CustomerGroup group) throws DuplicateNameException, AllExceptions {
		
		Optional<CustomerGroup> dbGroup = customerGroupRepository.findById(group.getGroupId());
		
		if (dbGroup.isEmpty()) {
			throw new AllExceptions("Group not found with ID: " + group.getGroupId());
		}
		CustomerGroup existingGroup = dbGroup.get();
		
		if (!group.getGroupName().equals(existingGroup.getGroupName())) {
			
			List<CustomerGroup> dbGroupList = customerGroupRepository.findAll();
			boolean flag = dbGroupList.stream().anyMatch(t -> t.getGroupName().equals(group.getGroupName()));
			if (flag) {
				throw new DuplicateNameException("Group name already exists");
			}
		}
		existingGroup.setGroupName(group.getGroupName());
		CustomerGroup savedGroup = customerGroupRepository.save(existingGroup);
		referenceRepository.deleteByGroupId(savedGroup.getGroupId());
		List<Customer> updatedCustomers = group.getCustomers();
		if (updatedCustomers != null) {
			for (Customer customer : updatedCustomers) {
				Optional<Customer> foundCustomer = customerRepository.findById(customer.getCustomerId());
				if (foundCustomer.isPresent()) {
					Customer dbCustomer = foundCustomer.get();
					CustomerGroupReference reference = new CustomerGroupReference();
					reference.setCustomerId(dbCustomer.getCustomerId());
					reference.setGroupId(savedGroup.getGroupId());
					referenceRepository.save(reference);
				}

			}

		}

		return savedGroup;

	}

	@Override
	public List<CustomerGroup> getCustomerByGroupId(int groupId) {
		List<CustomerGroup> customerGroups = new ArrayList<>();

		Optional<CustomerGroup> foundGroup = customerGroupRepository.findById(groupId);

		if (foundGroup.isPresent()) {
			CustomerGroup group = foundGroup.get();
			List<CustomerGroupReference> references = referenceRepository.findByGroupId(group.getGroupId());

			List<Customer> customers = new ArrayList<>();

			for (CustomerGroupReference cref : references) {
				Optional<Customer> customer = customerRepository.findById(cref.getCustomerId());

				if (customer.isPresent()) {
					Customer cust = customer.get();
					customers.add(cust);
				} else {
					System.out.println("Customer not found for CustomerGroupReference: " + cref);
				}
			}

			CustomerGroup customerGroup = new CustomerGroup();
			customerGroup.setGroupName(group.getGroupName());
			customerGroup.setCustomers(customers);
			customerGroup.setGroupId(group.getGroupId());

			customerGroups.add(customerGroup);
		} else {
			System.out.println("CustomerGroup not found for groupId: " + groupId);
		}

		return customerGroups;
	}
	
	@Override
	public List<CustomerGroupReference> getRefByGroupId(int groupId) {
		
		List<CustomerGroupReference> ref = referenceRepository.findByGroupId(groupId);
		
		return ref;
	}


}