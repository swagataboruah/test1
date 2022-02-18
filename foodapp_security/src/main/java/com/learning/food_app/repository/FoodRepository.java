package com.learning.food_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.food_app.dto.Food;
@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
	Boolean existsByFoodid(int foodid);
	Optional<List<Food>> findByFoodtype(String foodtype);
}
