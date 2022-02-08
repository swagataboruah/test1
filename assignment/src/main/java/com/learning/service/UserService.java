//user service interface

package com.learning.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.learning.Exception.AlreadyExistsException;
import com.learning.Exception.IdNotFoundException;
import com.learning.Exception.InvalidEmailException;
import com.learning.Exception.InvalidPasswordException;
import com.learning.dto.Register;


public interface UserService {

	public Register addUser(Register register) throws AlreadyExistsException;
	public Register updateUser(int id, Register register) throws IdNotFoundException;
	public Register getUserById(int id) throws IdNotFoundException;
	public Register[] getAllUsers() throws InvalidEmailException, InvalidPasswordException, SQLException;
	public String deleteUserById(int id) throws IdNotFoundException;
	public Optional<List<Register>> getAllUserDetails();
	
	
}