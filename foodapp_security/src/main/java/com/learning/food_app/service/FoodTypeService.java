package com.learning.food_app.service;

import java.util.Optional;

import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.dto.FoodType;

public interface FoodTypeService {

	public String addFoodType(FoodType foodtype);
	public void deleteFoodType(int foodtypeid) throws IdNotFoundException;
	public Optional<FoodType> getFoodTypeById(int foodtypeid);
}
