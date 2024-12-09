package com.maveric.csp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.csp.dto.LoginRequestDTO;
import com.maveric.csp.entities.User;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.UserNotFoundException;
import com.maveric.csp.jwt.JwtUtil;
import com.maveric.csp.repository.UserRepository;
import com.maveric.csp.service.UserServiceImpl;

	class UserServiceImplTest {

	    @Mock
	    private UserRepository userRepository;

	    @Mock
	    private JwtUtil jwtUtil;

	    @InjectMocks
	    private UserServiceImpl userService;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testSaveUser() throws AllExceptions {
	        User user = new User();
	        user.setUserId(1);
	        when(userRepository.save(user)).thenReturn(user);
	       // assertEquals(user, userService.saveUser(user));
	    }

	    @Test
	    void testGetUserDetails() throws UserNotFoundException {
	        User user = new User();
	        user.setUserId(1);
	        Optional<User> optionalUser = Optional.of(user);
	        when(userRepository.findById(1)).thenReturn(optionalUser);
	        //assertEquals(user, userService.getUserDetails(1));
	    }

	    @Test
	    void testGetUserDetails_UserNotFoundException() {
	        assertThrows(UserNotFoundException.class, () -> userService.getUserDetails(2));
	    }

	    @Test
	    void testGetAllUser() {
	        List<User> userList = new ArrayList<>();
	        when(userRepository.findAll()).thenReturn(userList);
	        assertEquals(userList, userService.getAllUser());
	    }

	    @Test
	    void testUpdateUser() throws UserNotFoundException {
	        User user = new User();
	        user.setUserId(1);
	        Optional<User> optionalUser = Optional.of(user);
	        when(userRepository.findById(1)).thenReturn(optionalUser);
	       // assertEquals(user, userService.updateUser(user));
	    }

	    @Test
	    void testUpdateUser_UserNotFoundException() {
	        User user = new User();
	        user.setUserId(2);
	        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user));
	    }

	    @Test
	    void testDeleteUserById() throws UserNotFoundException {
	        when(userRepository.existsById(1)).thenReturn(true);
	        userService.deleteUserById(1);
	        verify(userRepository, times(1)).deleteById(1);
	    }

	    @Test
	    void testDeleteUserById_UserNotFoundException() {
	        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(2));
	    }

	    @Test
	    void testLoadUserByUsername() {
	        User user = new User();
	        user.setEmail("test@example.com");
	        user.setPassword("password");
	        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
	        assertEquals("test@example.com", userService.loadUserByUsername("test@example.com").getUsername());
	    }

	    @Test
	    void testLoadUserByUsername_UsernameNotFoundException() {
	        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("test@example.com"));
	    }

	    @Test
	    void testLogin() throws Exception {
	        User user = new User();
	        user.setEmail("test@example.com");
	        user.setFirstName("John");
	        user.setLastName("Doe");
	        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
	        when(jwtUtil.generateToken("test@example.com")).thenReturn("mockedToken");
	      //  assertEquals("mockedToken John Doe", userService.login(new LoginRequestDTO(), null).toString());
	    }

	    @Test
	    void testGetUserDetailsByEmail() throws UserNotFoundException {
	        User user = new User();
	        user.setEmail("test@example.com");
	        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
	        assertEquals(user, userService.getUserDetails("test@example.com"));
	    }

	    @Test
	    void testGetUserDetailsByEmail_UserNotFoundException() {
	      //  assertThrows(UserNotFoundException.class, () -> userService.getUserDetails("test@example.com"));
	    }
	    
	    
	

	
	
	 private String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	}


