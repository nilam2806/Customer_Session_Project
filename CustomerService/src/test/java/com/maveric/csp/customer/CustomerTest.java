package com.maveric.csp.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.maveric.csp.entities.Customer;
import com.maveric.csp.entities.PreferredInvestmentProduct;
import com.maveric.csp.entities.WealthMode;

import jakarta.validation.Validator;

public class CustomerTest {

	private Validator validator;



	@Test
	public void testCustomerCreation() {
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("John Doe");
		customer.setCustomerEmail("john.doe@example.com");
		customer.setWealthMode(WealthMode.COMBO);
		customer.setPreferredProduct(PreferredInvestmentProduct.EQUITY_STOCKS);
		customer.setFinancialGoal("Retirement");

		assertEquals(1L, customer.getCustomerId());
		assertEquals("John Doe", customer.getCustomerName());
		assertEquals("john.doe@example.com", customer.getCustomerEmail());
		assertEquals(WealthMode.COMBO, customer.getWealthMode());
		assertEquals(PreferredInvestmentProduct.EQUITY_STOCKS, customer.getPreferredProduct());
		assertEquals("Retirement", customer.getFinancialGoal());
	}

}
