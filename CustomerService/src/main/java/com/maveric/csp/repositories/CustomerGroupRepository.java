package com.maveric.csp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maveric.csp.entities.CustomerGroup;

@Repository
public interface CustomerGroupRepository extends JpaRepository<CustomerGroup, Integer>{
	
	public Optional<CustomerGroup> findByGroupName(String groupname);

}
