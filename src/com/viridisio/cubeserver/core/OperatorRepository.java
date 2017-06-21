package com.viridisio.cubeserver.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.viridisio.cubeserver.utility.LocalDbConnector;

public class OperatorRepository {

	private static OperatorRepository instance;
	
	private static ArrayList<OperatorDefinition> allOperators = new ArrayList<OperatorDefinition>();
	
	public static OperatorRepository getInstantce() {
		
		if (instance == null)
			instance = new OperatorRepository();
		
		return instance;
	}
	
	private OperatorRepository() {
	Connection con = LocalDbConnector.getConnection();
		
		try 
	    {  
			String sql = "select * from operator";
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			
			ResultSet ret = pst.executeQuery();
			OperatorDefinition opdf = null;
	        while(ret.next())
	        {
	        	opdf = new OperatorDefinition();
	        	opdf.sign = ret.getString(1);  
	        	opdf.leftOperatorType = ret.getString(2); 
	        	opdf.rightOperatorType = ret.getString(3);  
	        	opdf.resultType = ret.getString(4);  
	        	opdf.className = ret.getString(5);  
	        	allOperators.add(opdf);
	        }
	        
		    con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}   
	}
	
	public Operator getOperator(String sign, String leftType, String rightType) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		for (OperatorDefinition opdef : allOperators) {
			if ( opdef.sign.equals(sign) && opdef.leftOperatorType.equals(leftType) && opdef.rightOperatorType.equals(rightType)) {
					Class c= Class.forName(opdef.className);
				    Operator op = (Operator)c.newInstance();
				    return op; 
			}
		}
		
		return null;
	}
}
