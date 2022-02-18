package com.learning.food_app.dto;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter

public class Role {
	
	@Id //Id must be auto generated
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int roleid;
	
	//it should be the value from available Enums
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private EROLE rolename;
	

}