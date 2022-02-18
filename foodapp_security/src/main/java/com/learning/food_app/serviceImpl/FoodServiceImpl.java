package com.learning.food_app.serviceImpl;

import java.util.List;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.food_app.Exception.AlreadyExistsException;
import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.dto.Food;
import com.learning.food_app.repository.FoodRepository;
import com.learning.food_app.service.FoodService;
@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	FoodRepository foodRepository;
	
			
    //Insert a new record in the table
	@Override
	public Food addFood(Food food) throws AlreadyExistsException {
    food = foodRepository.save(food);
		if (food != null) {
			return food;
		} else {
			return null;
		}
	}
    
	//Updating the existing record
	@Override
	public Food updateFood(int foodid, Food food) throws IdNotFoundException {
		// TODO Auto-generated method stub
		if(!this.foodRepository.existsById(foodid))
			throw new IdNotFoundException("Sorry food not found");
		
		return foodRepository.save(food);
	}
    
	//retrive a record by id
	@Override
	public Food getFoodById(int foodid) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<Food> optional =  foodRepository.findById(foodid);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("Sorry food not found");
		}
		else {
			return optional.get();
		}
	}
    
	//Delete the record by id
	@Override
	public String deleteFoodById(int foodid) throws IdNotFoundException {
		Food optional;
		try {
			optional = this.getFoodById(foodid);
			if(optional==null) {
				throw new IdNotFoundException("Sorry food not found");
			}
			else {
				foodRepository.deleteById(foodid);
				return "food item deleted";
			}
		} catch (IdNotFoundException e) {
			
			e.printStackTrace();
			throw new IdNotFoundException(e.getMessage());
		}
	}
    
	//Retrieve all details
	@Override
	public Optional<List<Food>> getAllFoodDetails() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(foodRepository.findAll());
	}

	
	@Override
	public Optional<List<Food>> getAllfoodbytypes(String foodtype) throws IdNotFoundException {
		
		Optional<List<Food>> optional =  foodRepository.findByFoodtype(foodtype);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("Sorry food not found");
		}
		else {
			return Optional.ofNullable(optional.get());
		}
		
		//return Optional.ofNullable(foodRepository.findByFoodtype());
	}




}
