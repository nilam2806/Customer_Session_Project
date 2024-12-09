package com.maveric.csp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maveric.csp.entities.CustomerGroupReference;

@Repository
public interface CustomerGroupReferenceRepository extends JpaRepository<CustomerGroupReference, Integer>{
	
	public CustomerGroupReference findByGroupIdAndCustomerId(int groupId, long customerId);
	
	public List<CustomerGroupReference> findByGroupId(int groupId);

	public void deleteByGroupId(int groupId);
	
	

}
