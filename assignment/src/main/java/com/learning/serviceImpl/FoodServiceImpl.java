package com.learning.serviceImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.Exception.AlreadyExistsException;
import com.learning.Exception.IdNotFoundException;
import com.learning.Exception.InvalidEmailException;
import com.learning.Exception.InvalidPasswordException;
import com.learning.dto.Food;
import com.learning.dto.Login;
import com.learning.dto.Register;
import com.learning.repository.FoodRepository;
import com.learning.repository.LoginRepository;
import com.learning.repository.UserRepository;
import com.learning.service.FoodService;
import com.learning.service.LoginService;
import com.learning.service.UserService;

@Service // using this we get the singleton object
public class FoodServiceImpl implements FoodService {
	

	@Autowired
	private FoodRepository foodRepository;	
	@Autowired
	FoodService foodService;
	
	@Override
	public Food add(Food food) throws IdNotFoundException {
		boolean status = foodRepository.existsByFoodid(food.getFoodid()) ;
		if(status) {
			throw new IdNotFoundException("Sorry food not found");
		}
		Food food2 = foodRepository.save(food);
		return food2;	
	}


//update
	@Override
	public Food update(int id, Food food) throws IdNotFoundException {
		return foodRepository.save(food);
	}


//get food by id
	@Override
	public Food getFoodById(int id) throws IdNotFoundException {
		Optional<Food> optional = foodRepository.findById(id);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("sorry food not found");
		}
		else {
			return optional.get();
		}
	}


// get all foods
	@Override
	public Food[] getAllFoods() throws InvalidEmailException, InvalidPasswordException, SQLException {
		List<Food> list = foodRepository.findAll();
		Food[] array = new Food[list.size()];
		return list.toArray(array);
	}


//delete food by id
	@Override
	public String deleteFoodById(int id) throws IdNotFoundException {
		Food optional;
		try {
			optional = this.getFoodById(id);
			if(optional!=null) {
				throw new IdNotFoundException("food not found");
			}
			else {
				foodService.deleteFoodById(id);
				return "food deleted";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IdNotFoundException(e.getMessage());
		}
	}

//get all food details
	@Override
	public Optional<List<Food>> getAllFoodDetails() {
		return Optional.ofNullable(foodRepository.findAll());
	}

	

}