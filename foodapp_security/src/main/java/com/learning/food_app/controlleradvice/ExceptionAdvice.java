package com.learning.food_app.controlleradvice;

import java.util.HashMap;


import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.learning.food_app.Exception.AlreadyExistsException;
import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.Exception.apierror.ApiError;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
//this class should be called when any user-defined exception is called 
//throughout all the controller so marked as @ControllerAdvice
	
	@ExceptionHandler(AlreadyExistsException.class) //this handler is called when that throws statement is occured
	public ResponseEntity<?> RecordAlreadyExistsExceptionHandler(AlreadyExistsException e) {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Record already exists"+e.getMessage());
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
	
	
	/*
	 * @Valid should be customized, So hence we need
	 * 1. custom error details object with suberror(field error)
	 * 2. create the beans
	 * 3. prepare the list and methods
	 * 
	 */
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
	    apiError.setMessage("Validation Error");
	    apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());//fieldwise errors
	    apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
	    return buildResponseEntity(apiError);
	}
	
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		///if i want to make any changes into our existing object then in every return we need to make changes
		//but instead of that if we use ResponseEntity method we will achieve ease of maintenance
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleConstraintViolation() {
		return null;
	}
	
	
	
	
	
}
