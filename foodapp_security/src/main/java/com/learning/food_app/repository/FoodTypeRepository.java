package com.learning.food_app.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.food_app.dto.ECUISINE;
import com.learning.food_app.dto.FoodType;


@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType, Integer> {

	Optional<FoodType> findByFoodtype(ECUISINE foodtype);
}
