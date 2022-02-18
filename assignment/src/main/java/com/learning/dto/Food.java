package com.learning.dto;
import java.util.HashSet;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
//@EqualsAndHashCode
//@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity 
@Table(name="food")
public class Food implements Comparable<Food>
{

	@Id 
	@Column(name="foodid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int foodid;
	
	@NotNull
	private int cost;
	
	@NotBlank
	private String foodpic;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private ECUISINE foodtype;

	@Override
	public int compareTo(Food o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@ManyToMany
	@JoinTable(name = "food_foodtypes", joinColumns = @JoinColumn(name = "foodId"), 
			inverseJoinColumns = @JoinColumn(name = "foodTypeId"))
	private Set<FoodType> foodTypes = new HashSet<>();



}



