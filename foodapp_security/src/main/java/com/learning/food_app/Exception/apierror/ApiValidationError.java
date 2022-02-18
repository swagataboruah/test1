package com.learning.food_app.Exception.apierror;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) //it will not call super.hashcode/super.equals

public class ApiValidationError extends ApiSubError {

	private String object; //which object has given error
	private String field; //on which field we have error
	private Object rejectedValue; //what was the reason for error
	private String message; //the message
	
	public ApiValidationError(String object, String message) {
		this.message = message;
		this.object = object;
	}
}
