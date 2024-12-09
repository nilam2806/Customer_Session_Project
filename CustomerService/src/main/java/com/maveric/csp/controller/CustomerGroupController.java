package com.maveric.csp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maveric.csp.entities.CustomerGroup;
import com.maveric.csp.entities.CustomerGroupReference;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.DuplicateNameException;
import com.maveric.csp.services.CustomerGroupService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/customerGroup")
public class CustomerGroupController {

	private static final Logger log = LoggerFactory.getLogger(CustomerGroupController.class);

	@Autowired
	CustomerGroupService customerGroupService;

	@Operation(summary = "Api to create CustomerGroup")
	@PostMapping("/create")
	public ResponseEntity<CustomerGroup> createCustomerGroup(@RequestBody CustomerGroup group)
			throws DuplicateNameException {

		log.info("CustomerGroupController : createCustomerGroup() : Call Started");
		log.info("RequestBody = " + group);

		CustomerGroup custGroup = customerGroupService.createCustomerGroup(group);

		if (custGroup != null) {
			return new ResponseEntity<CustomerGroup>(custGroup, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Api to Fetch Customer By GroupName")
	@GetMapping("/getCustomerByGroupName/{groupName}")
	public ResponseEntity<List<CustomerGroup>> getCustomerByGroupName(@PathVariable("groupName") String groupName) {

		List<CustomerGroup> customerList = customerGroupService.getCustomerByGroupName(groupName);

		if (customerList != null) {
			return new ResponseEntity<List<CustomerGroup>>(customerList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Operation(summary = "Api to Fetch Customer By GroupName")
	@GetMapping("/getByGroupId/{GroupId}")
	public ResponseEntity<List<CustomerGroup>> getCustomerByGroupId(@PathVariable("GroupId") int GroupId) {

		List<CustomerGroup> customerList = customerGroupService.getCustomerByGroupId(GroupId);

		if (customerList != null) {
			return new ResponseEntity<List<CustomerGroup>>(customerList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Operation(summary = "Api to Delete CustomerGroup By groupId")
	@DeleteMapping("/{groupId}")
	public ResponseEntity<Map<String, String>> deleteCustomerGroup(@PathVariable("groupId") int groupId)
			throws AllExceptions {

		String deleted = customerGroupService.deleteCustomerGroup(groupId);

		Map<String, String> map = new HashMap<>();

		if (deleted != null) {
			map.put("status", "CustomerGroupDeleted");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Operation(summary = "Api to Fetch all CustomerGroup")
	@GetMapping("/getGroupList")
	public ResponseEntity<List<CustomerGroup>> getGroupList() {

		List<CustomerGroup> groupList = customerGroupService.getAllCustomerGroup();

		if (groupList != null) {
			return new ResponseEntity<List<CustomerGroup>>(groupList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Operation(summary = "Api to update CustomerGroup")
	@PutMapping("/update")

	public ResponseEntity<CustomerGroup> updateCustomerGroup(@RequestBody CustomerGroup group)
			throws DuplicateNameException, AllExceptions {

		log.info("CustomerGroupController : updateCustomerGroup() : Call Started");
		log.info("RequestBody = " + group);

		CustomerGroup updatedCustGroup = customerGroupService.updateCustomerGroup(group);

		if (updatedCustGroup != null) {
			return new ResponseEntity<CustomerGroup>(updatedCustGroup, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	@Operation(summary ="Api to Fetch Customer By GroupName")
	@GetMapping("/getRefByGroupId/{GroupId}")
	public ResponseEntity<List<CustomerGroupReference>> getRefByGroupId(@PathVariable("GroupId") int GroupId){
		
		List<CustomerGroupReference> ref =  customerGroupService.getRefByGroupId(GroupId);
		
		if (ref != null) {
			return new ResponseEntity<List<CustomerGroupReference>>(ref, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}


}
