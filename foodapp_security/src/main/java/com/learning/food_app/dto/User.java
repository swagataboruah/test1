package com.learning.food_app.dto;

import java.util.HashSet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.learning.food_app.dto.Role;
import com.learning.food_app.dto.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import lombok.*;
@Data
@Setter
@Getter
@ToString
@NoArgsConstructor
//@AllArgsConstructor// removing this because id is auto generated
//instead of allargs we will write the constructor

@Entity 
@Table(name="register", uniqueConstraints = 
{@UniqueConstraint(columnNames = "username"),
@UniqueConstraint(columnNames = "email")}  )
public class User implements Comparable<User> {


	@Id // it will consider this column as PK
	@GeneratedValue(strategy = GenerationType.AUTO) //automatically generate register id
	@Column(name="regid")
	private Long id;
	
	@Size(max=50) //maximum length is 50
	@NotBlank //should not be a blank
	private String username;
	
	@Size(max=50)
	@Email
	private String email;
	
	@Size(max=100) //larger size as we will store encrypted pwd
	@NotBlank
	private String password;	

	@Size(max=100) 
	@NotBlank
	private String address;
	



	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "regId"), inverseJoinColumns = @JoinColumn(name = "roleId") )//relationship btwn registered user(regId) and role(roleId)
	private Set<Role> roles = new HashSet<>();
	
	
//	@OneToOne(mappedBy = "register", cascade = CascadeType.ALL)
//	@JsonIgnoreProperties({"hibernateLazyInitializer","handler","login"})
//	private Login login;
	
	
	public User(String username, String email, String password, String address) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.address = address;
	}


	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
		
}