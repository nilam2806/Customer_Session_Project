package com.maveric.csp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;


//	@NotNull(message = "email cannot be null")
//	@NotBlank(message = "emailId cannot be blank or null")
	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	private String email;

	
//	@NotNull(message = "password cannot be null")
	@NotBlank(message = "password cannot be blank or null")
	private String password;

	
//	@NotNull(message = "First Name cannot be null")
//	@NotBlank(message = "First name cannot be blank or null")
	private String firstName;


//	@NotNull(message = "Last Name cannot be null")
//	@NotBlank(message = "Last name cannot be blank or null")
	private String lastName;

	private String createdOn;

	private String lastLogin;

}
