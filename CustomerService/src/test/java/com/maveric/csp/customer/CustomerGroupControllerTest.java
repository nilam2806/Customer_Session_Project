package com.maveric.csp.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.maveric.csp.controller.CustomerGroupController;
import com.maveric.csp.entities.CustomerGroup;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.DuplicateNameException;
import com.maveric.csp.services.CustomerGroupService;

@ExtendWith(MockitoExtension.class)
public class CustomerGroupControllerTest {

    @InjectMocks
    private CustomerGroupController customerGroupController;

    @Mock
    private CustomerGroupService customerGroupService;

    @Test
    public void testCreateCustomerGroup() throws DuplicateNameException {
        CustomerGroup group = new CustomerGroup();
        group.setGroupName("TestGroup");

        when(customerGroupService.createCustomerGroup(group)).thenReturn(group);

        ResponseEntity<CustomerGroup> responseEntity = customerGroupController.createCustomerGroup(group);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(group, responseEntity.getBody());
    }

    @Test
    public void testGetCustomerByGroupName() {
        String groupName = "TestGroup";
        List<CustomerGroup> customerList = new ArrayList<>();

        when(customerGroupService.getCustomerByGroupName(groupName)).thenReturn(customerList);

        ResponseEntity<List<CustomerGroup>> responseEntity = customerGroupController.getCustomerByGroupName(groupName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerList, responseEntity.getBody());
    }

    @Test
    public void testDeleteCustomerGroup() throws AllExceptions {
        int groupId = 1;
        String deleted = "Deleted";

        when(customerGroupService.deleteCustomerGroup(groupId)).thenReturn(deleted);

        ResponseEntity responseEntity = customerGroupController.deleteCustomerGroup(groupId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetGroupList() {
        List<CustomerGroup> groupList = new ArrayList<>();

        when(customerGroupService.getAllCustomerGroup()).thenReturn(groupList);

        ResponseEntity<List<CustomerGroup>> responseEntity = customerGroupController.getGroupList();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(groupList, responseEntity.getBody());
    }

    @Test
    public void testUpdateCustomerGroup() throws DuplicateNameException, AllExceptions {
        CustomerGroup group = new CustomerGroup();
        group.setGroupId(1);
        group.setGroupName("TestGroup");

        when(customerGroupService.updateCustomerGroup(group)).thenReturn(group);

        ResponseEntity<CustomerGroup> responseEntity = customerGroupController.updateCustomerGroup(group);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(group, responseEntity.getBody());
    }
}
