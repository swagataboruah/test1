package com.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.Food;

@Repository //to get singleton object
public interface FoodRepository extends JpaRepository<Food, Integer> {

	Boolean existsByFoodid(int foodid);
}
