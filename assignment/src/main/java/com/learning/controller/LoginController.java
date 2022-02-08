package com.learning.controller;

import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.Exception.AlreadyExistsException;
import com.learning.dto.Register;
import com.learning.service.UserService;

@RestController 
@RequestMapping("/authenticate")
public class LoginController {

	@Autowired
	UserService userService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> addUser(@Valid @RequestBody Register register) throws AlreadyExistsException, SQLException {	
			
			Register result = userService.addUser(register);			
			System.out.println(result);
			return ResponseEntity.status(201).body(result); //201 means that it is created
			//here we need to returned json object with message record already exists
				
	}
	
}
