package com.learning.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data //to auto-generate getter and setters
@Setter
@Getter
@ToString //used to get String representation of output
@AllArgsConstructor //to automatically generate private constructor of each field
@NoArgsConstructor //to automatically generate private no arguement constructor

@Entity //this register class we want to use for ORM mapping so mark Entity
@Table(name="register") //jpa creates table and we can customize it using Table name

public class Register {
	
	@Id // it will consider this column as PK
	@GeneratedValue(strategy = GenerationType.AUTO) //automatically generate register id
	@Column(name="regid")
	private int id;
	
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
	

	//one user can have only 1 login
	@OneToOne(mappedBy = "register", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler","login"})
	//@JsonIgnore
	//@JsonSerialize(using = CustomListSerializer.class)
	private Login login;
	
		
}