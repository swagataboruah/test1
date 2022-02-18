package com.learning.food_app.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.food_app.dto.EROLE;
import com.learning.food_app.dto.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByRolename(EROLE rolename);
}
