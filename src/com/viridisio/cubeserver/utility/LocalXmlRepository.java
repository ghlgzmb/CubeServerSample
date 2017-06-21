package com.viridisio.cubeserver.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

public class LocalXmlRepository {

	public static Document getXml(String id)
	{		
		Connection con = LocalDbConnector.getConnection();
		Document xml = null;
		try 
	    {  
			String sql = "select * from xmlfile where id ='" + id + "'";
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			
			ResultSet ret = pst.executeQuery();
	        ret.next();
	        
	        String xmlString = ret.getString(2);
	        xml = DocumentHelper.parseText(xmlString);
	        
		    con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}  
	    return xml;
	}
	
	public static void updateXml(String id, Document xml)
	{		
		Connection con = LocalDbConnector.getConnection();
		try 
	    {  
			String sql = "update xmlfile set xml = '" + xml.asXML() + "' where id = '" + id + "'";
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			
			pst.execute();
	        
	        con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}  
	}
	
	public static String createXml(Document xml)
	{
		Connection con = LocalDbConnector.getConnection();
		String id = UUID.randomUUID().toString();
		try 
	    {  
			String sql = "insert into xmlfile (id,xml) values ('" + id + "' , '" + xml.asXML() + "')";
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			
			pst.execute();
	        
	        con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}
		return id;
	}
	
	public static void deleteXml(String id)
	{
		Connection con = LocalDbConnector.getConnection();

		try 
	    {  
			String sql = "delete from xmlfile where id = '" + id +"'";
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			
			pst.execute();
	        
	        con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}  
	}
}
