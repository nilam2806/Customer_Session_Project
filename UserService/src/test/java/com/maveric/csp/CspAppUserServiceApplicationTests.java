package com.maveric.csp;
 

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.ctc.wstx.shaded.msv_core.reader.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jdk14.JDK14Util;
import com.maveric.csp.controllers.UserController;
import com.maveric.csp.dto.LoginRequestDTO;
import com.maveric.csp.dto.LoginResponseDTO;
import com.maveric.csp.entities.User;
import com.maveric.csp.exceptions.UserNotFoundException;
import com.maveric.csp.service.UserService;

 
@ExtendWith(MockitoExtension.class)
class CspAppUserServiceApplicationTests {
 
    private MockMvc mockMvc;
 
    @Mock
    private UserService userService;
    
    @Mock
    private JDK14Util jwtUtil;
    
    
    @InjectMocks
    private Controller controller;
    
    
 
    @InjectMocks
    private UserController userController;
 
    

    // Utility method to convert object to JSON string
    private String asJsonString1(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
 
 
    @Test
    public void testGetUserDetails() throws Exception {
        User user = new User(); // create a user object
//        when(userService.getUserDetails(anyInt())).thenReturn(user);
 
//        mockMvc.perform(get("/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(user.getUserId()));
//
//        verify(userService, times(1)).getUserDetails(anyInt());
    }
 
    @Test
    public void testGetAllUser() throws Exception {
        User user1 = new User();
        User user2 = new User();
        List<User> userList = Arrays.asList(user1, user2);
 
   //  when(userService.getAllUser()).thenReturn(userList);
 
//        mockMvc.perform(get("/users/getAll"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].username").value(user1.getUserId()))
//                .andExpect(jsonPath("$[1].username").value(user2.getUserId()));
 
//        verify(userService, times(1)).getAllUser();
    }

    @Test
    public void testGetUserDetailsById() throws UserNotFoundException {
        int userId = 123;
        User user = new User();
        // Stubbing the userService.getUserDetails(userId) method to return a user object
        doReturn(user).when(userService).getUserDetails(userId);
 
        // Calling the method under test
        ResponseEntity<User> response = userController.getUserDetails(userId);
 
        // Verifying the response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        // Verifying that userService.getUserDetails(userId) is called
        verify(userService).getUserDetails(userId);
    }

    @Test
    public void testGetAllUser1() {
        // Create sample list of users
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
 
        // Stubbing the userService.getAllUser() method to return the sample list of users
        doReturn(userList).when(userService).getAllUser();
 
        // Calling the method under test
        ResponseEntity<List<User>> response = userController.getAllUser();
 
        // Verifying the response status code and body
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(userList, response.getBody());
        // Verifying that userService.getAllUser() is called
        verify(userService).getAllUser();
    }
    @Test
    public void testDeleteById() throws UserNotFoundException {
        int userId = 123;
 
       
        doNothing().when(userService).deleteUserById(userId);
 
     
        String response = userController.deleteById(userId);
 
     
        assertEquals("User Deleted Successfully", response);
 
      
        verify(userService).deleteUserById(userId);
    }

    @Test
    public void testUpdateUser() throws UserNotFoundException {
        User user = new User(); // Create a user object for testing
 
      
        when(userService.updateUser(user)).thenReturn(user);
 
 
        ResponseEntity<User> response = userController.updateUser(user);
 
       
        assertEquals(HttpStatus.OK, response.getStatusCode());
 
       
        assertEquals(user, response.getBody());
 
       
        verify(userService).updateUser(user);
    }
 
    @Test
    public void testGetAllUser11() {
        // Create a list of users for testing
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
 
        // Mock the userService.getAllUser method to return the list of users
        when(userService.getAllUser()).thenReturn(userList);
 
        // Call the getAllUser method of the UserController
        ResponseEntity<List<User>> response = userController.getAllUser();
 
        // Verify that the response entity has a status code of OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
 
        // Verify that the list of users returned in the response body is the same as the one we mocked
        assertEquals(userList, response.getBody());
 
        // Verify that userService.getAllUser was called
        verify(userService).getAllUser();
    }

    @Test
    public void testGetUserDetails1() throws UserNotFoundException {
        
        User user = new User();
 
        
        when(userService.getUserDetails(1)).thenReturn(user);
 
     
        ResponseEntity<User> response = userController.getUserDetails(1);
 
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
 
     
        assertEquals(user, response.getBody());
 
       
        verify(userService).getUserDetails(1);
    }
    
    
    @Test
    public void testSaveUser() {
        
        User user = new User();

    
        when(userService.saveUser(user)).thenReturn(user);

      
        ResponseEntity<User> response = userController.saveUser(user);

      
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    
        assertEquals(user, response.getBody());

       
        verify(userService).saveUser(user);
    }
    
    @Test
    public void testGetSetEmail() {
        
        String email = "test@example.com";
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();

    
        loginRequestDTO.setEmail(email);
        String retrievedEmail = loginRequestDTO.getEmail();

        
        assertEquals(email, retrievedEmail);
    }

    @Test
    public void testGetSetPassword() {
        
        String password = "password123";
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();

     
        loginRequestDTO.setPassword(password);
        String retrievedPassword = loginRequestDTO.getPassword();

      
        assertEquals(password, retrievedPassword);
    }

    
    @Test
    public void testConstructorAndGetters() {
        
        String jwt = "sampleJWT";
        String loggedInUser = "testUser";

       
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(jwt, loggedInUser);

       
        assertEquals(jwt, loginResponseDTO.jwt());
        assertEquals(loggedInUser, loginResponseDTO.loggedInUser());
    }

    @Test
    public void testEqualsAndHashCode() {
        
        String jwt1 = "jwt1";
        String jwt2 = "jwt2";
        String user1 = "user1";
        String user2 = "user2";

      
        LoginResponseDTO response1 = new LoginResponseDTO(jwt1, user1);
        LoginResponseDTO response2 = new LoginResponseDTO(jwt1, user1);
        LoginResponseDTO response3 = new LoginResponseDTO(jwt2, user1);
        LoginResponseDTO response4 = new LoginResponseDTO(jwt1, user2);

        
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
     //  assertEquals(response1.hashCode(), response3.hashCode());
        assertNotEquals(response1, response3);
        assertNotEquals(response1, response4);
    }

    @Test
    public void testToString() {
        
        String jwt = "sampleJWT";
        String loggedInUser = "testUser";

        
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(jwt, loggedInUser);

       
        String expectedString = "LoginResponseDTO[jwt=" + jwt + ", loggedInUser=" + loggedInUser + "]";
        assertEquals(expectedString, loginResponseDTO.toString());
    }


    
    @Test
    public void testUserConstructorAndGetters() {
       
        User user = new User();
        user.setUserId(1);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setCreatedOn("2024-02-29");
        user.setLastLogin("2024-02-29");

       
        int userId = user.getUserId();
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String createdOn = user.getCreatedOn();
        String lastLogin = user.getLastLogin();

     
        assertEquals(1, userId);
        assertEquals("test@example.com", email);
        assertEquals("password123", password);
        assertEquals("John", firstName);
        assertEquals("Doe", lastName);
        assertEquals("2024-02-29", createdOn);
        assertEquals("2024-02-29", lastLogin);
    }

    @Test
    public void testNoArgsConstructor() {
       
        User user = new User();

       
        assertNotNull(user);
    }

    @Test
    public void testSetterAndGetters() {
        
        User user = new User();

        // When
        user.setUserId(1);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setCreatedOn("2024-02-29");
        user.setLastLogin("2024-02-29");

        
        assertEquals(1, user.getUserId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("2024-02-29", user.getCreatedOn());
        assertEquals("2024-02-29", user.getLastLogin());
    }

//    @Test
//    public void testAllArgsConstructor() {
//        // Given
//        String email = "test@example.com";
//        String password = "password123";
//        String firstName = "John";
//        String lastName = "Doe";
//        String createdOn = "2024-02-29";
//        String lastLogin = "2024-02-29";
//
//        // When
//        User user = new User();
//
//        // Then
//        assertNotNull(user);
//        assertNull(user.getUserId()); // As it should be generated by JPA
//      assertEquals(email, user.getEmail());
//       assertEquals(password, user.getPassword());
//        assertEquals(firstName, user.getFirstName());
//        assertEquals(lastName, user.getLastName());
//        assertEquals(createdOn, user.getCreatedOn());
//        assertEquals(lastLogin, user.getLastLogin());
//    }
//
//   
   

 
 
    // Similarly, write tests for other methods like updateUser, deleteUserById, login, etc.
 
    // Utility method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

