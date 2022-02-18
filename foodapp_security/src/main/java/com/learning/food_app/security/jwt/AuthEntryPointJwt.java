package com.learning.food_app.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	 private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	  @Override
	  //commence is used to take care of all exceptions regarding the unauthorized access
	  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
	      throws IOException, ServletException {
	    logger.error("Unauthorized error: {}", authException.getMessage());

	    response.setContentType(MediaType.APPLICATION_JSON_VALUE); //this response if of json type
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //status code as unauthorized

	    final Map<String, Object> body = new HashMap<>();
	    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
	    body.put("error", "Unauthorized");
	    body.put("message", authException.getMessage());
	    body.put("path", request.getServletPath());

	    final ObjectMapper mapper = new ObjectMapper(); //renders content interms of json
	    mapper.writeValue(response.getOutputStream(), body);
	  }
	

}
