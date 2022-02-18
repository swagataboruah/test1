package com.learning.food_app.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.dto.ECUISINE;
import com.learning.food_app.dto.EROLE;
import com.learning.food_app.dto.Food;
import com.learning.food_app.dto.FoodType;
import com.learning.food_app.dto.Role;
import com.learning.food_app.dto.User;
import com.learning.food_app.repository.FoodRepository;
import com.learning.food_app.repository.FoodTypeRepository;
import com.learning.food_app.repository.UserRepository;
import com.learning.food_app.service.FoodService;
import com.learning.food_app.payload.response.MessageResponse;
import com.learning.food_app.security.services.UserDetailsImpl;

@RestController
@RequestMapping("/api/food")
public class FoodController {
	
	@Autowired
	FoodRepository foodRepository;
	@Autowired
	FoodService foodService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	FoodTypeRepository foodtypeRepository;
	
	//POST /api/food/add
	//Adding new food into the system
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addFood(@Valid @RequestBody Food food) {
		
		if(foodRepository.existsByFoodid(food.getFoodid())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Food already exists!"));
		}
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long tokenid = userDetailsImpl.getId(); //retrieving the id from the jwt token 
        
        Optional<User> optional = userRepository.findById(tokenid); //finding the register object and passping it
        User registered_token = optional.get();
        
        food = new Food(food.getFoodid(),
        		food.getCost(),
        		food.getFoodpic(),
        		food.getDescription(),
        		food.getFoodtype());
        
//        Set<String> strType = food.getFoodtypes();
// 
//        Set<FoodType> foodtypes = new HashSet<>();
//        
//        if(strType==null) {
//     	   FoodType foodtype = foodtypeRepository.findByFoodtype(ECUISINE.INDIAN)
//     			   .orElseThrow(
//     					   ()->new RuntimeException("Error: Food type not found")
//     					   );
//     	   foodtypes.add(foodtype);
//        }    	
//        
//        else {
//        	strType.forEach(e->{
//     		   switch(e) {
//     		   case "mexican" :
//     			   FoodType mexican = foodtypeRepository.findByFoodtype(ECUISINE.MEXICAN)
//     			   			                      .orElseThrow(
//     			   			                      ()->new RuntimeException("Error: Food type not found"));
//     			   
//     			   foodtypes.add(mexican);
//     			   break;
//     			   
//     		   case "indian" :
//     			  FoodType indian = foodtypeRepository.findByFoodtype(ECUISINE.INDIAN)
// 		   			                            .orElseThrow(
// 		   			                            ()->new RuntimeException("Error: Food type not found"));
// 		   
//     			   foodtypes.add(indian);
//     			   break;
//     			   
//     			   
//     		  case "chineese" :
//     			  FoodType chineese = foodtypeRepository.findByFoodtype(ECUISINE.CHINEESE)
// 		   			                            .orElseThrow(
// 		   			                            ()->new RuntimeException("Error: Food type not found"));
// 		   
//     			   foodtypes.add(chineese);
//     			   break;
//     			   
//     		   }
//     	   });
//	};
//	
//	 food.setFoodtype(foodtypes);
	 foodRepository.save(food);
	 return ResponseEntity.status(201).body(new MessageResponse("Food inserted successfully!"));
			
}
	
	//GET /api/food/{id}	
	@GetMapping("/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable("id") int foodid) throws IdNotFoundException{
		
		if(foodRepository.existsByFoodid(foodid)) {
			Food result = foodService.getFoodById(foodid);
			return ResponseEntity.ok(result);	
		}
		else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Sorry Food not found"));
		}
				
	}
	
	//PUT /api/food/{id}
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateFood(@PathVariable("id") int foodid, @RequestBody Food food) throws IdNotFoundException
	{
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long tokenid = userDetailsImpl.getId(); //retrieving the id from the jwt token 
        
        Optional<User> optional = userRepository.findById(tokenid); //finding the register object and passping it
        User registered_token = optional.get();
        
		Food result = foodService.updateFood(foodid, food);
		return ResponseEntity.status(201).body(result);
	}
	
	
	//GET /api/food/all
	@GetMapping("/all")
	public ResponseEntity<?> getAllFoodDetails(){
		Optional<List<Food>> optional = foodService.getAllFoodDetails();
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional.get());	
		
	}
	
	
	//DELETE /api/food/{id}
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteFoodById(@PathVariable("id") int foodid) throws IdNotFoundException, SQLException
	{
		if(foodRepository.existsByFoodid(foodid)) {
			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        Long tokenid = userDetailsImpl.getId(); //retrieving the id from the jwt token 
	        
	        Optional<User> optional = userRepository.findById(tokenid); //finding the register object and passping it
	        User registered_token = optional.get();
	        
			String result = foodService.deleteFoodById(foodid);
			Map<String, String> map = new HashMap<>();
			map.put("message", "food item deleted");
			return ResponseEntity.status(201).body(result);
		}
		else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Food id not found"));
		}	
	
	}
	
	
	//GET FOOD BY FOOD TYPE
	@GetMapping("/foodbytype/{foodbytype}")
	public ResponseEntity<?> getfooddetailsbyType(@RequestBody Food food) throws IdNotFoundException{
		Optional<List<Food>> optional = foodService.getAllfoodbytypes(food.getFoodtype());
		
		if(optional.isEmpty()) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("message","no record found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		
		return ResponseEntity.of(optional);
	}
	
	
	
	
	
	
	
}
