package com.learning.food_app.Exception;
import lombok.ToString;

@ToString(callSuper = true)

public class AlreadyExistsException extends Exception {
	public AlreadyExistsException(String message) {
		super(message);
	}
}
