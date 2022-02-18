package com.learning.food_app.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.Exception.InvalidEmailException;
import com.learning.food_app.Exception.InvalidIdLengthException;
import com.learning.food_app.Exception.InvalidNameException;
import com.learning.food_app.Exception.InvalidPasswordException;
import com.learning.food_app.dto.User;
import com.learning.food_app.repository.RoleRepository;
import com.learning.food_app.repository.UserRepository;
import com.learning.food_app.security.services.UserDetailsImpl;
import com.learning.food_app.service.LoginService;
import com.learning.food_app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	LoginService loginService;
	
	//GET /api/users/all
	//retrieve all users
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<?> getAllUserDetails(){
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long tokenid = userDetailsImpl.getId(); //retrieving the id from the jwt token 
        
        Optional<User> optional = userRepository.findById(tokenid); //finding the register object and passping it
        User registered_token = optional.get();
		
		Optional<List<User>> optional1 = userService.getAllUserDetails();
		if(optional1.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional1.get());	
		
	}
	
	
	//GET /api/users/{id}	
	//retrieve user by id
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) throws IdNotFoundException, InvalidNameException, InvalidEmailException, InvalidPasswordException, InvalidIdLengthException {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long tokenid = userDetailsImpl.getId(); //retrieving the id from the jwt token 
        
        Optional<User> optional = userRepository.findById(tokenid); //finding the register object and passping it
        User registered_token = optional.get();
		User register = userService.getUserById(id);
		return ResponseEntity.ok(register);
	}
	
	
	//PUT /api/users/{id}
	//update user by id
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User register) throws IdNotFoundException
	{
		User result = userService.updateUser(id, register);
     	loginService.changePassword(result.getEmail(), result.getPassword());
		return ResponseEntity.status(201).body(result);
	}
	
	
	//DELETE /api/users/{id}
	//delete the user by id
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") long id) throws IdNotFoundException, SQLException
	{
		String result = userService.deleteUserById(id);
		Map<String, String> map = new HashMap<>();
		map.put("message", "User deleted successfully");
		return ResponseEntity.status(201).body(result);
	}
	
	
}
