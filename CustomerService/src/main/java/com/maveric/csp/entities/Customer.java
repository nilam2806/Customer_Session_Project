package com.maveric.csp.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Customer implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerId;
	
	private String customerName;
	
	private String customerEmail;
	
	@Enumerated(EnumType.STRING)
	private WealthMode wealthMode;

	@Enumerated(EnumType.STRING)
	private PreferredInvestmentProduct preferredProduct;
	
	@NotNull(message =" financialGoal cannot be null" )
	private String financialGoal;
	
}
