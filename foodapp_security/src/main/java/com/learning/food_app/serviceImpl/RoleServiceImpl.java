package com.learning.food_app.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.dto.Role;
import com.learning.food_app.repository.RoleRepository;
import com.learning.food_app.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String addRole(Role role) {
		// TODO Auto-generated method stub
		Role role2 = roleRepository.save(role);
		if(role2!=null) {
			return "success";
		} else {
			return "fail";
		}
		
	}

	//delete
	@Override
	public void deleteRole(int roleId) throws IdNotFoundException {
		Optional<Role> optional;
		optional = this.getRoleById(roleId);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("record not found");
		}
		else {
			roleRepository.deleteById(roleId);
			
	}
	}

	@Override
	public Optional<Role> getRoleById(int roleId) {
		return roleRepository.findById(roleId);
	}

}
