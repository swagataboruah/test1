package com.learning.food_app.controlleradvice;
//package com.learning.food_app.controlleradvice;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
////to get the separation between different errors
////even if this class is deleted no problem
//public class ErrorAdvice extends ResponseEntityExceptionHandler {
//
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		// TODO Auto-generated method stub
//		//return super.handleMethodArgumentNotValid(ex, headers, status, request);
//		return ResponseEntity.ok(ex);
//	}
//}
