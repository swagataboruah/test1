package com.learning.dto;
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
public class Login implements Comparable <Login> {
	

	@Id //to set as PK
	@Column(name="username")
	@Size(max=50)
	@NotBlank
	private String username;
	
	@Size(max=100)
	@NotBlank
	private String password;
	


	@Override
	public int compareTo(Login o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//one login can belong to only 1 register
	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) 
	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinColumn(name = "regid") //to create 3rd table using this
	private Register register;
	
}