package com.learning.food_app.serviceImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.food_app.Exception.AlreadyExistsException;
import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.Exception.InvalidEmailException;
import com.learning.food_app.Exception.InvalidIdLengthException;
import com.learning.food_app.Exception.InvalidNameException;
import com.learning.food_app.Exception.InvalidPasswordException;
import com.learning.food_app.dto.Login;
import com.learning.food_app.dto.User;
import com.learning.food_app.repository.LoginRepository;
import com.learning.food_app.repository.UserRepository;
import com.learning.food_app.service.LoginService;
import com.learning.food_app.service.UserService;

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
	//above line is used because when we want to rollback everytime we face AlreadyExistsException
	public User addUser(User register) throws AlreadyExistsException {
		boolean status = userRepository.existsByEmail(register.getEmail()) ;
		if(status) {
			throw new AlreadyExistsException("this record already exists");
		}
		User register2 = userRepository.save(register);
		if (register2 != null) {
			Login login = new Login(register.getEmail(), register.getPassword(), null, register2);
				if(loginRepository.existsByUsername(register.getEmail())) {
				throw new AlreadyExistsException("this record already exists");
			}
			String result = loginService.addCredentials(login);
			if(result == "success") {
					//return "record added in register and login";
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
		
				
	
//	//update
//	@Override
//	public String updateUser(Long id, User register) throws IdNotFoundException {
//		boolean i = this.userRepository.existsById(id);
//		if(!i)
//			throw new IdNotFoundException("invalid id");
//		if(this.userRepository.save(register)!= null) {
//			return "success";
//		}
//		else return "fail";		
//	}

	
	//get user by id
	@Override
	public User getUserById(Long id) throws IdNotFoundException {
		Optional<User> optional = userRepository.findById(id);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("id doesnt exist");
		}
		else {
			return optional.get();
		}
	}
	
	
	//get all users
	@Override
	public User[] getAllUsers() throws InvalidEmailException, InvalidNameException, InvalidPasswordException, InvalidIdLengthException, SQLException {
		List<User> list = userRepository.findAll();
		User[] array = new User[list.size()];
		return list.toArray(array);
	}

	
	//delete user by id
	@Override
	public String deleteUserById(Long id) throws IdNotFoundException {
		//cross check with findbyid
		//use optional here coz findbyid return optional type
		User optional;
			try {
				optional = this.getUserById(id);
				if(optional!=null) {
					throw new IdNotFoundException("record not found");
				}
				else {
					userRepository.deleteById(id);
					return "register record deleted";
				}
			} catch (IdNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IdNotFoundException(e.getMessage());
			}
		
	}
	
	
	//get all users details
	@Override
	public Optional<List<User>> getAllUserDetails() {
		return Optional.ofNullable(userRepository.findAll());
	}



	@Override
	public User updateUser(Long id, User register) throws IdNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	

}