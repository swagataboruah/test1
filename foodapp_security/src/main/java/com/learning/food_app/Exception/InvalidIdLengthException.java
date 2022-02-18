package com.learning.food_app.Exception;

import lombok.ToString;

@ToString(callSuper = true)
// toString from the base class ===> Exception 


//as an User Defined Exception.
public class InvalidIdLengthException extends Exception {

	public InvalidIdLengthException(String message) {
		super(message);
		
	}
	
//	@Override
//	public String toString() {
//		return "IdNotFoundException [toString()=" + super.toString() + "]";
//	}
	// this same thing can be done using lombok --> callSuper=true
	
	
	
}