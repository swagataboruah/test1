package com.learning.food_app.dto;
import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.*;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name="login")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "userName")
public class Login implements Comparable <Login> {
	

	@Id 
	@Column(name="username")
	@Size(max=50)
	@NotBlank
	private String username;
	@Size(max=100)
	@NotBlank
	private String password;
	@NotBlank
	private Long id;


	@Override
	public int compareTo(Login o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@OneToOne(fetch = FetchType.LAZY) 
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinColumn(name = "regId")
	private User register;
	
}