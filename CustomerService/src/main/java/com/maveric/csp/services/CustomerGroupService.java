package com.maveric.csp.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maveric.csp.entities.CustomerGroup;
import com.maveric.csp.entities.CustomerGroupReference;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.DuplicateNameException;

@Service
public interface CustomerGroupService {

	CustomerGroup createCustomerGroup(CustomerGroup group) throws DuplicateNameException;
	
	CustomerGroup updateCustomerGroup(CustomerGroup group) throws DuplicateNameException, AllExceptions;
	
	List<CustomerGroup> getCustomerByGroupName(String groupName);
	
	List<CustomerGroup> getAllCustomerGroup();
	
	String deleteCustomerGroup(int groupId) throws AllExceptions;

	List<CustomerGroup> getCustomerByGroupId(int groupId);

	List<CustomerGroupReference> getRefByGroupId(int groupId);
	
}
