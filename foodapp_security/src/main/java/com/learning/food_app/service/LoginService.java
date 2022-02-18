package com.learning.food_app.service;

import java.sql.SQLException;

import com.learning.food_app.Exception.IdNotFoundException;
import com.learning.food_app.dto.Login;
import com.learning.food_app.dto.Role;

public interface LoginService {

	public String addCredentials(Login login);

	public String deleteCredentials(String username) throws SQLException, IdNotFoundException;

	public String changePassword(String username,String password);

	public String changeRole(String username, Role role) throws SQLException;

}

