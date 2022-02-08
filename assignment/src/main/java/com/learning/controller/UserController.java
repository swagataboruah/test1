package com.learning.controller;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.Exception.AlreadyExistsException;
import com.learning.Exception.IdNotFoundException;
import com.learning.Exception.InvalidEmailException;
import com.learning.Exception.InvalidPasswordException;
import com.learning.dto.Login;
import com.learning.dto.Register;
import com.learning.service.LoginService;
import com.learning.service.UserService;

import antlr.collections.List;

@RestController 
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	LoginService loginService;
	
	
//adding
	@PostMapping("/add")
	public ResponseEntity<?> addUser(@Valid @RequestBody Register register) throws AlreadyExistsException, SQLException {	
			
			Register result = userService.addUser(register);			
			System.out.println(result);
			return ResponseEntity.status(201).body(result); //201 means that it is created
			//here we need to returned json object with message record already exists
				
	}
	
	
//retrieving specific record
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") int id) throws IdNotFoundException,  InvalidEmailException, InvalidPasswordException {
		Register register = userService.getUserById(id);
		return ResponseEntity.ok(register);
	}
	
	
	
//retrieving all records
	@GetMapping("/all")
	public ResponseEntity<?> getAllUsers() throws InvalidEmailException,InvalidPasswordException, IdNotFoundException, SQLException {
		
		Optional<java.util.List<Register>> optional =  userService.getAllUserDetails();
	   
	   if(optional.isEmpty()) {
		   Map<String, String> map = new HashMap<>();
		   map.put("message", "no record found");
		   return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		   
	   }
		return ResponseEntity.ok(optional.get());
	}
	
//update
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUserById(@PathVariable("id") int id, @RequestBody Register register) throws IdNotFoundException {
		Register result = userService.updateUser(id, register);
		return ResponseEntity.status(201).body(result);	
	}
	
//authentication	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody Login login){
		String result = loginService.authenticate(login);
		Map<String, String> map = new HashMap<>();
		map.put("message", "authenticate");
		if(result.equals("success")) {
			return ResponseEntity.status(201).body(result);
        
		}
		return ResponseEntity.status(200).body(result);
	
	}	
	
//deletebyid
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) throws IdNotFoundException, SQLException
	{
		String result = userService.deleteUserById(id);
		Map<String, String> map = new HashMap<>();
		map.put("message", "User deleted successfully");
		return ResponseEntity.status(201).body(result);
		
	}
	

	
	
	
}
