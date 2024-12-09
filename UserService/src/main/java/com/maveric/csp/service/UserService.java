package com.maveric.csp.service;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.maveric.csp.dto.LoginRequestDTO;
import com.maveric.csp.dto.LoginResponseDTO;
import com.maveric.csp.entities.User;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletResponse;

@Service
public interface UserService {
	
	public User saveUser(User user) throws AllExceptions;
	public User getUserDetails(int userId) throws UserNotFoundException;
	public List<User> getAllUser();
	public User updateUser(User user) throws UserNotFoundException;
	public void deleteUserById(int userId) throws UserNotFoundException;
    public LoginResponseDTO login(LoginRequestDTO loginRequest,HttpServletResponse response) throws IOException;
	public User getUserDetails(String email)throws UserNotFoundException; 
}
