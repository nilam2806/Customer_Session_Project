package com.maveric.csp.customer;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.maveric.csp.entities.Customer;
import com.maveric.csp.entities.CustomerGroup;

public class CustomerGroupTest {

    @Test
    public void testCustomerGroupCreation() {
        CustomerGroup group = new CustomerGroup();
        group.setGroupId(1);
        group.setGroupName("VIP Customers");

        assertEquals(1, group.getGroupId());
        assertEquals("VIP Customers", group.getGroupName());
    }

    @Test
    public void testCustomerList() {
        CustomerGroup group = new CustomerGroup();
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());
        group.setCustomers(customers);

        assertEquals(customers, group.getCustomers());
    }

    // Add more test cases as needed
}
