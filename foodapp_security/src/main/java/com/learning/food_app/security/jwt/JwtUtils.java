package com.learning.food_app.security.jwt;

import java.util.Date;

//expectation from JWT utils 
//->generate token 
//->validate token
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.learning.food_app.security.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

//provides the token
//there is only 1 instance 
@Component
public class JwtUtils {

	public static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    
	//read jwt secret
	@Value("${zee5_app.app.jwtSecret}")
	private String jwtSecret;
	
	//read exp value
	@Value("${zee5_app.app.jwtExpirationMs}")
	private int jwtExpiryValue;
		
	//generate token
	//Authentication taken from the spring framework
	public String generateToken(Authentication authentication) {
		
		//issed@ when token generated
		//expiry: 
		//encryption strategy
		
		
		
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		//username
		
		//expectation from builder method: used to build jwt object
		//in setSubject which property we need to share? -> we share the username so that we can create a unique token
		return Jwts.builder().setSubject(userPrincipal.getUsername())
		              .setIssuedAt(new Date())
		              .setExpiration(new Date(new Date().getTime()+jwtExpiryValue))
                      .signWith(SignatureAlgorithm.HS512,jwtSecret)
                      .compact();		
		}
	
	    //expectation is to get username from token 
		//above we set the username into Subject
		//parser will take things inside algorithm and decode it 
		//token needs to be transformed into JWS object based on that hs512
		//now it is a transformed object in terms of java
		//from this we retrive the body and then subject
		public String getUsernameFromJwtToken(String authToken) {
	
			return Jwts.parser()
					.setSigningKey(jwtSecret)
					.parseClaimsJws(authToken)
					.getBody().getSubject();
		}

		
//	private long jwtExpiryValue() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	
	
	//validate token
	public boolean ValidateJwtToken(String authToken) {
		
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
    
	}
	
	
	
	
	
	
	
	
}
