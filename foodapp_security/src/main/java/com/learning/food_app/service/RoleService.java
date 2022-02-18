package com.learning.food_app.service;
import java.util.Optional;

import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.dto.Role;

public interface RoleService {

	public String addRole(Role role);
	public void deleteRole(int roleId) throws IdNotFoundException;
	public Optional<Role> getRoleById(int roleId);
}
