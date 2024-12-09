package com.maveric.csp.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class CustomerGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int groupId;
	
	private String groupName;
	
	@Transient
	private List<Customer> customers;

}
