package com.learning.food_app.Exception.apierror;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
//this class should provide collective information on the error(s)
public class ApiError {

	private HttpStatus httpStatus;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy hh:mm:ss") //command to convert to string and give output to user
	private LocalDateTime timeStamp; //to give exact time stamp
	
	private String message; //error msg
	private String debugMessage;
	private List<ApiSubError> subErrors; //to hold validation related errors
	
	private ApiError () {
		timeStamp = LocalDateTime.now(); //at the time of calling the method whatever the date time value are there, this will provide
	}
	
	public ApiError(HttpStatus status) {
		this();
		this.httpStatus = status;
	}
	
	private ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.httpStatus = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
	
	//We need to hold every fields validation information in the subError
	//below method helps to "create" and "add" it into subError
	//private because we will use it internally 
	private void addSubError(ApiSubError apiSubError) {
		if(subErrors == null) {
			subErrors = new ArrayList<>();
		}
		subErrors.add(apiSubError);
	}
	
	private void addValidationError(String object, String field, Object rejectedValue, String message) {
		addSubError(new ApiValidationError(object, field, rejectedValue, message));
	}
	
	private void addValidationError(String Object, String message) {
		addSubError(new ApiValidationError(Object, message));
	}
	
	//fielderror = encapsulates a field error, this is, a reason for rejecting a specific field value
	private void addValidationError(FieldError fieldError) {
		this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
	}
	
	public void addValidationErrors(List<FieldError> fieldErrors) {
		//both the below given commands do the same thing and bot hcan be used
		//fieldErrors.forEach(this::addValidationError);
		fieldErrors.forEach(e->this.addValidationError(e));
	}
	
	//to take care of object errors
	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}
	
	public void addValidationError(List<ObjectError> globalErrors) {
		globalErrors.forEach(e->this.addValidationError(e));
	}
	
	public void addValidationError(ConstraintViolation<?> cv) {
		this.addValidationError((cv.getRootBeanClass().getName()),
				((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
				cv.getInvalidValue(),
				cv.getMessage());
		
		
	}
	
	public void addValidationsErrors(Set<ConstraintViolation<?>> constrianViolations) {
		constrianViolations.forEach(e->addValidationError(e));
	}
	
	
}
