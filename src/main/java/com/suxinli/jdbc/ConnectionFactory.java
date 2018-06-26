package com.suxinli.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.suxinli.config.Configuration;

public class ConnectionFactory {
	private static String url;
	private static String user;
	private static String password;
	
	static {
		/* read configuration */
		/* 1. register the driver */
		String drivers = Configuration.get("driver");
		try {
			Class.forName(drivers);
		} catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		
		
		url = Configuration.get("url");
		user = Configuration.get("username");
		password = Configuration.get("password");
		
	}
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
