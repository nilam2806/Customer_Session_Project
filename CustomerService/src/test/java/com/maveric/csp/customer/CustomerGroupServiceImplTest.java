package com.maveric.csp.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
import com.maveric.csp.entities.CustomerGroup;
import com.maveric.csp.entities.CustomerGroupReference;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.DuplicateNameException;
import com.maveric.csp.repositories.CustomerGroupReferenceRepository;
import com.maveric.csp.repositories.CustomerGroupRepository;
import com.maveric.csp.repositories.CustomerRepository;
import com.maveric.csp.services.CustomerGroupServiceImpl;

public class CustomerGroupServiceImplTest {

    @Mock
    private CustomerGroupRepository customerGroupRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerGroupReferenceRepository referenceRepository;

    @InjectMocks
    private CustomerGroupServiceImpl customerGroupService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCustomerGroup_Success() throws DuplicateNameException {
        CustomerGroup group = new CustomerGroup();
        group.setGroupName("TestGroup");

        when(customerGroupRepository.findByGroupName(group.getGroupName())).thenReturn(Optional.empty());
        when(customerGroupRepository.save(group)).thenReturn(group);

        CustomerGroup savedGroup = customerGroupService.createCustomerGroup(group);

        assertEquals(group, savedGroup);
        verify(customerGroupRepository).save(group);
    }

    @Test
    public void testCreateCustomerGroup_DuplicateName() {
        CustomerGroup group = new CustomerGroup();
        group.setGroupName("TestGroup");

        when(customerGroupRepository.findByGroupName(group.getGroupName())).thenReturn(Optional.of(group));

        assertThrows(DuplicateNameException.class, () -> {
            customerGroupService.createCustomerGroup(group);
        });
    }

    @Test
    public void testGetCustomerByGroupName_Success() {
        String groupName = "TestGroup";
        CustomerGroup group = new CustomerGroup();
        group.setGroupName(groupName);

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("Customer1");

        CustomerGroupReference reference = new CustomerGroupReference();
        reference.setCustomerId(customer.getCustomerId());
        reference.setGroupId(group.getGroupId());

        List<CustomerGroupReference> references = new ArrayList<>();
        references.add(reference);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerGroupRepository.findByGroupName(groupName)).thenReturn(Optional.of(group));
        when(referenceRepository.findByGroupId(group.getGroupId())).thenReturn(references);
        when(customerRepository.findById(reference.getCustomerId())).thenReturn(Optional.of(customer));

        List<CustomerGroup> customerGroups = customerGroupService.getCustomerByGroupName(groupName);

        assertEquals(1, customerGroups.size());
        assertEquals(groupName, customerGroups.get(0).getGroupName());
        assertEquals(customers.size(), customerGroups.get(0).getCustomers().size());
        assertEquals(customers.get(0).getCustomerName(), customerGroups.get(0).getCustomers().get(0).getCustomerName());
    }

    @Test
    public void testDeleteCustomerGroup_Success() throws AllExceptions {
        int groupId = 1;
        CustomerGroup group = new CustomerGroup();
        group.setGroupId(groupId);

        when(customerGroupRepository.findById(groupId)).thenReturn(Optional.of(group));

        customerGroupService.deleteCustomerGroup(groupId);

        verify(customerGroupRepository).deleteById(groupId);
        verify(referenceRepository).findByGroupId(groupId);
        verify(referenceRepository).deleteAll(any());
    }

    @Test
    public void testDeleteCustomerGroup_GroupNotFound() {
        int groupId = 1;

        when(customerGroupRepository.findById(groupId)).thenReturn(Optional.empty());

        assertThrows(AllExceptions.class, () -> {
            customerGroupService.deleteCustomerGroup(groupId);
        });
    }

    @Test
    public void testUpdateCustomerGroup_Success() throws DuplicateNameException, AllExceptions {
        CustomerGroup group = new CustomerGroup();
        group.setGroupId(1);
        group.setGroupName("TestGroup");

        CustomerGroup existingGroup = new CustomerGroup();
        existingGroup.setGroupId(1);
        existingGroup.setGroupName("ExistingGroup");

        when(customerGroupRepository.findById(group.getGroupId())).thenReturn(Optional.of(existingGroup));
        when(customerGroupRepository.save(existingGroup)).thenReturn(existingGroup);

        CustomerGroup updatedGroup = customerGroupService.updateCustomerGroup(group);

        assertEquals(group.getGroupName(), updatedGroup.getGroupName());
        verify(referenceRepository).deleteByGroupId(group.getGroupId());
    }
}
