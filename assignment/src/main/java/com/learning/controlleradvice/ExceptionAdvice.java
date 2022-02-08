package com.learning.controlleradvice;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.learning.Exception.AlreadyExistsException;
import com.learning.Exception.IdNotFoundException;
import com.learning.service.UserService;

@ControllerAdvice
public class ExceptionAdvice {
//this class should be called when any user-defined exception is called 
//throughout all the controller so marked as @ControllerAdvice
	
	@ExceptionHandler(AlreadyExistsException.class) //this handler is called when that throws statement is occured
	public ResponseEntity<?> RecordAlreadyExistsExceptionHandler(AlreadyExistsException e) {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(map);		
	}
	
	@ExceptionHandler(Exception.class) //if the 1st one doesnt work ie. no match then 
	//internally spring takes care 
	public ResponseEntity<?> ExceptionHandler(Exception e) {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "unknown exception"+ e.getMessage());
		return ResponseEntity.badRequest().body(map);		
	}
	
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> idNotFoundExceptionHandler(IdNotFoundException e) {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(map);		
		
	}
	
	
	
	
	
	
	
	
	
}
