package com.learning.food_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.learning.food_app.security.jwt.AuthEntryPointJwt;
import com.learning.food_app.security.jwt.AuthTokenFilter;
import com.learning.food_app.security.services.UserDetailsServiceImpl;


@Configuration //it will have only security related configurations
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //through prePostEnabled=true we can confirm the accessibility of the user whether some information should be available to the user with or without security  
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
		
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Override
	//manages authentication
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
		
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	//endpoint accesibility
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()  //cors -> cross origin support, in this case using cors one domin and talk to other domain -> to give the cross domin support  //and used to call/build other methods
                                            //csrf -> cross site request -> used for multiple requests from front end
		                                    //disabling csrf 
	      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
	      .antMatchers("/api/test/**").permitAll().anyRequest().authenticated(); //permit any request after authentication

	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); //these 2 filters are added into the system
	}
	
	
	
	
	
}
