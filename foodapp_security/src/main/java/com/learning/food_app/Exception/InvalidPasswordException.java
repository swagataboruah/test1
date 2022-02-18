package com.learning.food_app.Exception;
import lombok.ToString;

@ToString(callSuper = true)

public class InvalidPasswordException extends Exception {

	public InvalidPasswordException(String message) {
		super(message);
		
	}
}
