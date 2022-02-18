package com.learning.food_app.controller;

import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.food_app.dto.EROLE;
import com.learning.food_app.dto.Role;
import com.learning.food_app.dto.User;
import com.learning.food_app.payload.request.LoginRequest;
import com.learning.food_app.payload.request.SignupRequest;
import com.learning.food_app.payload.response.JwtResponse;
import com.learning.food_app.payload.response.MessageResponse;
import com.learning.food_app.repository.RoleRepository;
import com.learning.food_app.repository.UserRepository;
import com.learning.food_app.security.jwt.JwtUtils;
import com.learning.food_app.security.services.UserDetailsImpl;
import com.learning.food_app.service.UserService;

@RestController 
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	AuthenticationManager authenticationManager;	
	
	//get authorities will help to get authority details -> internally it will provide the user details -> which will be converted to string and traversed -> from that we get the authority and put into list of string
	//POST /api/authenticate (singin)
	//AUTHENTICATE USER
	@PostMapping("/authenticate")
	 public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
						, loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateToken(authentication);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetailsImpl.getAuthorities()
				.stream()
				.map(i->i.getAuthority())
				.collect(Collectors.toList());
		
		
	
		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetailsImpl.getId(),
				userDetailsImpl.getUsername(),
				userDetailsImpl.getEmail(),
				roles));
		
		//this will create an access token as output -> using this we need to confirm our validation -> so we created testcontroller
	}

		
	//POST /api/register (singup)
	//ADD NEW USER TO DB
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
		//why do we have siguprequest here? -> 
		
		if(userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
       if(userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already taken!"));
		}
       
       User user = new User(signupRequest.getUsername(),
    		   signupRequest.getEmail(),
    		   passwordEncoder.encode(signupRequest.getPassword()),
    		   signupRequest.getAddress());
       		   
       
       //retrieving role details
       Set<String> strRoles = signupRequest.getRole();
       
       Set<Role> roles = new HashSet<>();
       
       if(strRoles==null) {
    	   Role userRole = roleRepository.findByRolename(EROLE.ROLE_USER)
    			   .orElseThrow(
    					   ()->new RuntimeException("Error: Role not found")
    					   );
    	   roles.add(userRole);
       }
       else {
    	   strRoles.forEach(e->{
    		   switch(e) {
    		   case "admin" :
    			   Role roleAdmin = roleRepository.findByRolename(EROLE.ROLE_ADMIN)
    			   			                      .orElseThrow(
    			   			                      ()->new RuntimeException("Error: role not found"));
    			   
    			   roles.add(roleAdmin);
    			   break;
    			   
    		   case "user" :
    			   Role roleUser = roleRepository.findByRolename(EROLE.ROLE_USER)
		   			                            .orElseThrow(
		   			                            ()->new RuntimeException("Error: role not found"));
		   
		           roles.add(roleUser);
    			   break;
    			   
    		   }
    	   });
    	   	   
       }
       user.setRoles(roles);
	   userRepository.save(user);
	   return ResponseEntity.status(201).body(new MessageResponse("User created successfully!"));
		
	
}	

}
