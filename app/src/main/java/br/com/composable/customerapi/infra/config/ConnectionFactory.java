package br.com.composable.customerapi.infra.config;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionFactory {

	
	private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/customer_api_db?useTimezone=true&serverTimezone=UTC"; 
	private static final String USER = "root"; 
	private static final String PASS = "root";     
	
	public static Connection getConnection()  { 
		try {
			Connection connection = DriverManager.getConnection(URL_MYSQL, USER, PASS);
			connection.setAutoCommit(false);
			
			return connection;
		
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    } 

}
