package com.learning.food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.food_app.dto.Login;
@Repository
public interface LoginRepository extends JpaRepository<Login, String> {

	Boolean existsByUsername(String username);
}
