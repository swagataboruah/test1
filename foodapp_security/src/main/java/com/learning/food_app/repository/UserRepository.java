package com.learning.food_app.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.food_app.dto.User;

@Repository 
public interface UserRepository extends JpaRepository<User, Long> {

	//as per practice these custom jpa checking should be done in repository
	//so repository must have this method
	//but this will be called inside service only
	//only declare, no body for thee
	
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);
	Optional<User> findById(Long id);


	
}
