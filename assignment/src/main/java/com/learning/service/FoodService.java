//user service interface

package com.learning.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.learning.Exception.AlreadyExistsException;
import com.learning.Exception.IdNotFoundException;
import com.learning.Exception.InvalidEmailException;
import com.learning.Exception.InvalidPasswordException;
import com.learning.dto.Food;
import com.learning.dto.Register;


public interface FoodService {

	public Food add(Food food) throws IdNotFoundException;
	public Food update(int id, Food food) throws IdNotFoundException;
	public Food getFoodById(int id) throws IdNotFoundException;
	public Food[] getAllFoods() throws InvalidEmailException, InvalidPasswordException, SQLException;
	public String deleteFoodById(int id) throws IdNotFoundException;
	public Optional<List<Food>> getAllFoodDetails();
	
	
}