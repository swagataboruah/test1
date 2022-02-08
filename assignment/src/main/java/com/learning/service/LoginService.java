package com.learning.service;

import java.sql.SQLException;


import com.learning.Exception.IdNotFoundException;
import com.learning.dto.Login;

public interface LoginService {

	public String addCredentials(Login login);

	public String deleteCredentials(String username) throws SQLException, IdNotFoundException;

	public String authenticate(Login login);

	
}

