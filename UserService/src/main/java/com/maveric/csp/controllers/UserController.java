package com.maveric.csp.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maveric.csp.dto.LoginRequestDTO;
import com.maveric.csp.dto.LoginResponseDTO;
import com.maveric.csp.entities.User;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.UserNotFoundException;
import com.maveric.csp.jwt.JwtUtil;
import com.maveric.csp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Operation(summary = "API to Create User")
	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) throws AllExceptions {
		log.info("UserController : saveUser() : Call Started");
		log.info("RequestBody = " + user);
		User savedUser = userService.saveUser(user);
		log.info("UserController : saveUser() : Call Ended");
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	@Operation(summary = "API to Get user details by Id")
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserDetails(@PathVariable int userId) throws UserNotFoundException {
		log.info("UserController : getUserById() : Call Started");
		log.info("UserId= " + userId);
		User user = userService.getUserDetails(userId);
		log.info("UserController : getUserById() : Call Ended");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Operation(summary = "API to Get all Users")
	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getAllUser() {
		log.info("UserController : getAll() : Call Started");
		List<User> allUser = userService.getAllUser();
		log.info("UserController : getAll() : Call Ended");
		return new ResponseEntity<>(allUser, HttpStatus.OK);
	}

	@Operation(summary = "API to update User details")
	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) throws UserNotFoundException {
		log.info("UserController : updateUser() : Call Started");
		User updatedUser = userService.updateUser(user);
		log.info("UserController : updateUser() : Call Ended");
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@Operation(summary = "Api to delete User")
	@DeleteMapping("/delete/{userId}")
	public String deleteById(@Parameter(description = "User of this ID will delete") @PathVariable int userId) throws UserNotFoundException {
		log.info("UserController : deleteById() : Call Started");
		userService.deleteUserById(userId);
		log.info("UserController : deleteById() : Call Ended");
		return "User Deleted Successfully";
	}

	@Operation(summary = "Api for user login")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response)
			throws IOException,BadCredentialsException,DisabledException {
		log.info("UserController : login() : Call Started");
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		LoginResponseDTO loginresponse = userService.login(loginRequest, response);
		log.info("UserController : login() : Call Ended");
		return ResponseEntity.ok(loginresponse);
	}
	
	@GetMapping("/validate")
	public String validateToken(@PathVariable("token") String token)
	{
		jwtUtil.validateToken(token);
		return "Token is valid";
	}
	
	@Operation(summary = "API to Get user details by Id")
	@GetMapping("/getby/{email}")
	public ResponseEntity<User> getUserDetails(@PathVariable String email) throws UserNotFoundException {
		log.info("UserController : getUserById() : Call Started");
		log.info("UserId= " + email);
		User user = userService.getUserDetails(email);
		log.info("UserController : getUserById() : Call Ended");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
