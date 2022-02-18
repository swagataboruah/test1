
package com.learning.food_app.Exception;

import lombok.ToString;

@ToString(callSuper = true)

public class IdNotFoundException extends Exception {

	public IdNotFoundException(String message) {
		super(message);
	}
}
