package com.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.Register;

@Repository //to get singleton object
public interface UserRepository extends JpaRepository<Register, Integer> {

	//contains the custom authentication for email 
	Boolean existsByEmail(String email);
	
}
