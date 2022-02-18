package com.learning.food_app.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.learning.food_app.Exception.AlreadyExistsException;
import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.dto.Food;
import com.learning.food_app.dto.FoodType;

public interface FoodService {

	public Food addFood(Food food) throws AlreadyExistsException;
	public Food updateFood(int foodid, Food food) throws IdNotFoundException;
	public Food getFoodById(int foodid) throws IdNotFoundException;
	public String deleteFoodById(int foodid) throws IdNotFoundException;
	public Optional<List<Food>> getAllFoodDetails();
	public Optional<List<Food>> getAllfoodbytypes(String foodtype) throws IdNotFoundException;
}
