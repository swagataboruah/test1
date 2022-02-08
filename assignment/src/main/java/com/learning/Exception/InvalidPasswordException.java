package com.learning.Exception;
import lombok.ToString;

@ToString(callSuper = true)

public class InvalidPasswordException extends Exception {

	public InvalidPasswordException(String message) {
		super(message);
		
	}
}
