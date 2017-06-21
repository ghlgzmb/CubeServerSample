package com.viridisio.cubeserver.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalDbConnector {

	public static Connection getConnection(){
		
		String url = "jdbc:mysql://127.0.0.1:3306/cubeserver?characterEncoding=utf8&useSSL=false";  
		String name = "com.mysql.jdbc.Driver";  
		String user = "root";  
		String password = "gaohe0311";  
		
	    try {
			Class.forName(name);
			return DriverManager.getConnection(url, user, password);//获取连接  
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	    
	    return null;
	    
	}
}
