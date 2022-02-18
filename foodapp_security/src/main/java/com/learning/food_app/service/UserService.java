//user service interface

package com.learning.food_app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.learning.food_app.Exception.AlreadyExistsException;
import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.Exception.InvalidEmailException;
import com.learning.food_app.Exception.InvalidIdLengthException;
import com.learning.food_app.Exception.InvalidNameException;
import com.learning.food_app.Exception.InvalidPasswordException;
import com.learning.food_app.dto.User;


public interface UserService {

	public User addUser(User register) throws AlreadyExistsException;
	public User updateUser(Long id, User register) throws IdNotFoundException;
	public User getUserById(Long id) throws IdNotFoundException;
	public User[] getAllUsers() throws InvalidEmailException, InvalidNameException, InvalidPasswordException, InvalidIdLengthException, SQLException;
	public String deleteUserById(Long id) throws IdNotFoundException;
	public Optional<List<User>> getAllUserDetails();
	
}