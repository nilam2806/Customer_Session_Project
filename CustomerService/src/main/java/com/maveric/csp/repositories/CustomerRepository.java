
package com.maveric.csp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maveric.csp.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByCustomerName(String customerName);

//	default Optional<Customer> findByCustomerId(int customerId);
}
