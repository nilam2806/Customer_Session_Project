package com.maveric.csp.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.maveric.csp.conversion.AppConversions;
import com.maveric.csp.dto.LoginRequestDTO;
import com.maveric.csp.dto.LoginResponseDTO;
import com.maveric.csp.entities.User;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.UserNotFoundException;
import com.maveric.csp.jwt.JwtUtil;
import com.maveric.csp.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;
	@Autowired
    JwtUtil jwtUtil;

	@Override
	public User saveUser(User user) throws AllExceptions {
		log.info("UserServiceImpl : saveUser() : Call Started");
		LocalDateTime currentDate = LocalDateTime.now();
		String formatedDate = AppConversions.convertDateToString(currentDate);
		user.setCreatedOn(formatedDate);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User savedUser = userRepository.save(user);
		if (savedUser == null) {
			throw new AllExceptions("while saving user");
		}
		log.info("UserServiceImpl : saveUser() : Call Ended");
		return savedUser;
	}

	@Override
	public User getUserDetails(int userId) throws UserNotFoundException {
		log.info("UserServiceImpl : getUserById() : Call Started");
		Optional<User> foundUser = userRepository.findById(userId);
		if (!foundUser.isPresent()) {
			throw new UserNotFoundException("User not found.Please enter valid UserId");
		} else {
			log.info("UserServiceImpl : getUserById() : Call Ended");
			return foundUser.get();
		}

	}

	@Override
	public List<User> getAllUser() {
		log.info("UserServiceImpl : getAllUser() : Call Started");
		List<User> allUser = userRepository.findAll();
		if (allUser.isEmpty()) {
			log.info("List is empty");
		}
		log.info("UserServiceImpl : getAllUser() : Call Ended");
		return allUser;
	}

	public User updateUser(User user) throws UserNotFoundException {
		User updatedUser = null;
		Optional<User> databaseUser = userRepository.findById(user.getUserId());

		if (databaseUser.isPresent()) {
			User dbUser = databaseUser.get();
			dbUser.setEmail((user.getEmail() == null ? dbUser.getEmail() : user.getEmail()));
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			dbUser.setPassword(user.getPassword() == null ? dbUser.getPassword() : encodedPassword);
			dbUser.setFirstName(user.getFirstName() == null ? dbUser.getFirstName() : user.getFirstName());
			dbUser.setLastName(user.getLastName() == null ? dbUser.getLastName() : user.getLastName());
			dbUser.setCreatedOn(user.getCreatedOn() == null ? dbUser.getCreatedOn() : user.getCreatedOn());
			dbUser.setLastLogin(user.getLastLogin() == null ? dbUser.getLastLogin() : user.getLastLogin());
			return updatedUser;
		} else {
			throw new UserNotFoundException("User not found");
		}

	}

	@Override
	public void deleteUserById(int userId) throws UserNotFoundException {
		log.info("UserServiceImpl : deleteUserById() : Call Started");
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException("User not found.Please enter valid UserId");
		} else {
			log.info("UserServiceImpl : deleteUserById() : Call Ended");
			userRepository.deleteById(userId);
		}

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				Collections.emptyList());
	}

	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequest, HttpServletResponse response) throws IOException {
		final UserDetails userDetails = loadUserByUsername(loginRequest.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		// update lastLogin when user loggedin
		User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
				() -> new UsernameNotFoundException("Customer not found with email: " + loginRequest.getEmail()));
		LocalDateTime currentDate = LocalDateTime.now();
		String formatedDate = AppConversions.convertDateToString(currentDate);
		user.setLastLogin(formatedDate);
		User updatedUser = userRepository.save(user);
		String loggedInUser = updatedUser.getFirstName() + " " + updatedUser.getLastName();
		return new LoginResponseDTO(jwt, loggedInUser);
	}

	@Override
	public User getUserDetails(String email)throws UserNotFoundException {
		
		Optional<User> foundUser = userRepository.findByEmail(email);
		if (foundUser.isPresent()) {
			
			return foundUser.get();
		}else {
			
			throw new UsernameNotFoundException("User not found");
			
		}
		
	}
	
	
}
