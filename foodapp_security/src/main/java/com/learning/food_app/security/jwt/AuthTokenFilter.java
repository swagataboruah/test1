package com.learning.food_app.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.learning.food_app.security.services.UserDetailsServiceImpl;

//this class is reponsible for managing the token
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	@Override
	//doFilter -> every request passes through oncefilter and internally it passes through dofilter
	//this method is reponsible for validating token
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		try {
		String jwt = parseJwt(request);
		if(jwt!=null && jwtUtils.ValidateJwtToken(jwt)) {
			String username = jwtUtils.getUsernameFromJwtToken(jwt); //based on this we can get the username
			//based on this we can create usernamepasswordauthentication
			//this we can set into authentication
			//and this object we can get from authenticationamanger
			
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request)); //from this request we will propogate the token and other details
			
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); 		

		}} catch(Exception e) {
			logger.error(e.getMessage());
			logger.error("cannot set user authentication {}", e);
		}
		filterChain.doFilter(request, response); //internally it will call next filter or Servlet(DS)

	}
		
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		//the jwt token is placed inside header (header = authorization header)
		
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
			return headerAuth.substring(7, headerAuth.length()); //ie, from the 7th index onwards we will get actual token
		}
		return null;
	}	
	

}
