package com.learning.serviceImpl;

import java.sql.SQLException;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.Exception.IdNotFoundException;
import com.learning.dto.Login;
import com.learning.dto.Register;
import com.learning.repository.LoginRepository;
import com.learning.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginRepository loginRepository;
	Login login = null;
	Register register = null;
	
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
	public String authenticate(Login login) {
		login = new Login();
		register = new Register();		
		if(login.getUsername()==register.getEmail() && login.getPassword()==register.getPassword()) {
			return "success";
		}
		else
			return "fail";
	}
}

	
