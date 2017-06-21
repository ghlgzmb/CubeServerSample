package com.viridisio.cubeserver.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.viridisio.cubeserver.utility.LocalDbConnector;

public class ConstantFactory {
	
	public static Map<String,String> constantTypes = new HashMap<String,String>();
	
	static{
		Connection con = LocalDbConnector.getConnection();
		
		try 
	    {  
			String sql = "select * from constanttype";
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			
			ResultSet ret = pst.executeQuery();
			
	        while(ret.next())
	        {
	        	constantTypes.put(ret.getString(1),ret.getString(2));
	        }
	        
		    con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}   
	}
	
	public static IConstant createConstant(String type, String value)
	{
		IConstant cons = null;
		String className = constantTypes.get(type);
		if (className != null)
		{
			Class c;
			try {
				c = Class.forName(className);
				cons = (IConstant)c.newInstance();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (value != null)
				cons.filledFromString(value);
		}
		
		return cons;
	}

	
}
