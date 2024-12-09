package com.maveric.csp.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.maveric.csp.entities.CustomerGroupReference;

public class CustomerGroupReferenceTest {

    @Test
    public void testCustomerGroupReferenceCreation() {
        CustomerGroupReference reference = new CustomerGroupReference();
        reference.setId(1);
        reference.setGroupId(100);
        reference.setCustomerId(200);

        assertEquals(1, reference.getId());
        assertEquals(100, reference.getGroupId());
        assertEquals(200, reference.getCustomerId());
    }

    // Add more test cases as needed
}
