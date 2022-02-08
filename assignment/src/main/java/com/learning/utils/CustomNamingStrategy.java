package com.learning.utils;

import org.hibernate.boot.model.naming.Identifier;


import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	private final static String POSTFIX = "_table";
	//here we specify that all tables should end with name_table
	//we want to set this as thumb rule
	
	@Override
	public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment context) {
		// TODO Auto-generated method stub
		if(identifier==null) {
			return null;
		}
		final String newName = identifier.getText()+POSTFIX;
		return identifier.toIdentifier(newName);
				//table name
				//1. if@table annotation is available then it will use that name
				//2. if @table not there then it will take entity name if entity 
				//name is not available then by default it will take class name as entity name
	}
	
	@Override
	public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment context) {
		if(identifier==null) {
			return null;
		}
		return Identifier.toIdentifier(identifier.getText().toLowerCase());
	}
	
	
	
	
	
	
	
	
	
	
}
