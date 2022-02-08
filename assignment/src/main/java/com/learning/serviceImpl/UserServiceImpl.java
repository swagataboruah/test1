package com.learning.serviceImpl;
import java.sql.SQLException;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.Exception.AlreadyExistsException;
import com.learning.Exception.IdNotFoundException;
import com.learning.Exception.InvalidEmailException;
import com.learning.Exception.InvalidPasswordException;
import com.learning.dto.Login;
import com.learning.dto.Register;
import com.learning.repository.LoginRepository;
import com.learning.repository.UserRepository;
import com.learning.service.LoginService;
import com.learning.service.UserService;

@Service // using this we get the singleton object
public class UserServiceImpl implements UserService {
	

	@Autowired
	private UserRepository userRepository;		
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private LoginService loginService;
	
	
//add
	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = AlreadyExistsException.class)
	public Register addUser(Register register) throws AlreadyExistsException {
		
		boolean status = userRepository.existsByEmail(register.getEmail()) ;
		if(status) {
			throw new AlreadyExistsException("EMAIL ALREADY EXISTS!");
		}		
		
		Register register2 = userRepository.save(register);
		if (register2 != null) {
			Login login = new Login(register.getUsername(), register.getPassword(), register2);
			    if(loginRepository.existsByUsernameAndPassword(register.getEmail(), register.getPassword())) {
				    throw new AlreadyExistsException("USER ALREADY EXISTS");
			     }
			    String result = loginService.addCredentials(login);
			    if(result == "success") {
				    	return register2;
			     }
			else {
				return null;
			}
		}	
		else {
			return null;
		}
	}	
				

//update
	@Override
	public Register updateUser(int id, Register register) throws IdNotFoundException {
//		if(!this.userRepository.existsById(id))
//			throw new IdNotFoundException("Sorry user with " + id + " not found");
//		
		return userRepository.save(register);
	}


//get user by id
	@Override
	public Register getUserById(int id) throws IdNotFoundException {
		Optional<Register> optional = userRepository.findById(id);
		Register register = new Register();
		if(optional.isEmpty()) {
			throw new IdNotFoundException("sorry" + register.getId() + "not found");
		}
		else {
			return optional.get();
		}
	}
	
	
//get all users
	@Override
	public Register[] getAllUsers() throws InvalidEmailException, InvalidPasswordException,SQLException {
		List<Register> list = userRepository.findAll();
		Register[] array = new Register[list.size()];
		return list.toArray(array);
	}

	
//delete user by id
	@Override
	public String deleteUserById(int id) throws IdNotFoundException {
			
		Register optional; 
		Register register = new Register();
			try {
				optional = this.getUserById(id);
				if(optional!=null) {
					throw new IdNotFoundException("sorry user with id" + id + "not found");
				}
				else {
					userRepository.deleteById(id);
					return "User Successfully deleted";
				}
			} catch (IdNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IdNotFoundException(e.getMessage());
			}
		
	}

	@Override
	public Optional<List<Register>> getAllUserDetails() {
		return Optional.ofNullable(userRepository.findAll());
	}

	

}