package com.learning.food_app.security.services;

import java.util.Collection;



import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.food_app.dto.User;

import lombok.Data;
@Data

public class UserDetailsImpl implements UserDetails {

	private Long id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	//authorities are basically Roles
	private Collection<? extends GrantedAuthority> authorities;
	
	
	//Parameterized constructor
	public UserDetailsImpl(Long id, String username, String email, String password,
		      Collection<? extends GrantedAuthority> authorities) {
		    this.id = id;
		    this.username = username;
		    this.email = email;
		    this.password = password;
		    this.authorities = authorities;
		  }
		
		
	//role specifications are available in "authorities"
	//but somewhere we have to read these "authorities" and it should be available to
	//the UserDeatilsImpl object, so for that we create static method build
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(role->new SimpleGrantedAuthority(role.getRolename().toString()))
				.collect(Collectors.toList()); 		
		//getRoles is set and it will return set
		//from roles we only want the role
		//stream transforms set to stream //stream is the flow of data
		//map
		
		return new UserDetailsImpl(user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				authorities);
						
	}
	
	@Override
	//this method is used to get authorities ie. role specifications
		//role specifications are available in "authorities"
		//but somewhere we have to read these "authorities" and it should be available to
		//the UserDeatilsImpl object, so for that we create static method build
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean equals(Object o) {
        if (this == o)
		     return true;
		if (o == null || getClass() != o.getClass())
		     return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		     return Objects.equals(id, user.id);
	}	
	
	
	
}
