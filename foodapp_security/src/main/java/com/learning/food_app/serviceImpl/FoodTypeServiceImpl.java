package com.learning.food_app.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.dto.FoodType;
import com.learning.food_app.repository.FoodTypeRepository;
import com.learning.food_app.service.FoodTypeService;
@Service
public class FoodTypeServiceImpl implements FoodTypeService {

	@Autowired
	FoodTypeRepository foodtypeRepository;
	
	
	@Override
	public String addFoodType(FoodType foodtype) {
		// TODO Auto-generated method stub
		FoodType foodType2 = foodtypeRepository.save(foodtype);
		if(foodType2!=null)
			return "food added";
		return "fail";
	}

	@Override
	public void deleteFoodType(int foodtypeid) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<FoodType> optional;
		optional = this.getFoodTypeById(foodtypeid);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("record not found");
		}
		else {
			foodtypeRepository.deleteById(foodtypeid);
			
	}

	}

	@Override
	public Optional<FoodType> getFoodTypeById(int foodtypeid) {
		// TODO Auto-generated method stub
		return foodtypeRepository.findById(foodtypeid);
	}
}
