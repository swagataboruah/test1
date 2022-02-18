package com.learning.food_app.serviceImpl;

import java.sql.SQLException;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.dto.Login;
import com.learning.food_app.dto.Role;
import com.learning.food_app.repository.LoginRepository;
import com.learning.food_app.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginRepository loginRepository;

	@Override
	public String addCredentials(Login login) {
		Login login2 = loginRepository.save(login);
		if(login2!=null) {
			return "success";
		} else {
			return "fail";
		}
	}

	@Override
	public String deleteCredentials(String userName) throws SQLException, IdNotFoundException {
		
		try {
		Optional<Login> optional = loginRepository.findById(userName);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("record not found");
		}
		else {
			loginRepository.deleteById(userName);
			return "success";
		} 
		} catch ( IdNotFoundException e) {
			e.printStackTrace();
			throw new IdNotFoundException(e.getMessage());
		}
	}

	@Override
	public String changePassword(String userName, String password) {
		return null;
	}

	@Override
	public String changeRole(String userName, Role role) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}