package com.viridisio.cubeserver.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.viridisio.cubeserver.utility.LocalDbConnector;

public class CubeRepository {
	
	private static CubeRepository instance;
	
	public Map<String, String> types = new HashMap<String, String>();

	public ArrayList<Cube> cubes = new ArrayList<Cube>();
	
	public static CubeRepository getInstance()
	{
		if (instance == null)
			instance = new CubeRepository();
		
		return instance;
	}
	
	public CubeRepository() {
		
		Connection con = LocalDbConnector.getConnection();
		
		try 
	    {  
			String sql = "select * from cubetype";
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			
			ResultSet ret = pst.executeQuery();
			String type = null;
			String typeClassName = null;
	        while(ret.next())
	        {
	        	type = ret.getString(1);  
	        	typeClassName = ret.getString(2);  
	        	types.put(type, typeClassName);
	        }
	        
		    con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}  
	}

	public Cube getCube(String cubeId)
	{
		for (Cube c : cubes)
		{
			if (cubeId.equals(c.id))
				return c;
		}
		
		Connection con = LocalDbConnector.getConnection();
		Cube cube = null;
		
		try 
	    {  
			String sql = "select * from cube where cid='"+cubeId+"'";
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			
			ResultSet ret = pst.executeQuery();
	        ret.next(); 
	        
	        String cubeType = ret.getString(2);

	        Class c;
			try {
				c = Class.forName(types.get(cubeType));
		        cube = (Cube)c.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

				e.printStackTrace();
			}
	        
	        cube.id = ret.getString(1);
	        cube.cubeType = cubeType;  
	        String parentCubeId = ret.getString(3);
	        if (parentCubeId != null)
	        	cube.parent =parentCubeId;
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd");
			cube.lastModifiedTime = sdf.parse(ret.getString(4));
	        String content = ret.getString(5);
	        Document xml = DocumentHelper.parseText(content);
	        cube.fillFromXml(xml);
	        
		    con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}  
 
		this.cubes.add(cube);
		return cube;
	}
	
	public void saveCube(Cube cube)
	{
		Connection con = LocalDbConnector.getConnection();
		
		try 
	    {  	
			Document xml = cube.saveToXml();
			String contentString = xml.asXML();
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd");
			cube.lastModifiedTime = new Date();
			String lastModifiedString = sdf.format(cube.lastModifiedTime);
			String sql = "update cube set modifydate = '" + lastModifiedString + "' ,content = '"+contentString + "' ,pid = '"+cube.parent +"' where cid = '"+cube.id+"'"; 
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			pst.execute();
	        
		    con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}  
	}
	
	public void deleteCube(String cubeId)
	{
		Connection con = LocalDbConnector.getConnection();
		
		try 
	    {  
			Cube c= this.getCube(cubeId);
			c.beforeDelete();
			
			String sql = "delete from cube where cid='"+ cubeId+"'";
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			
			pst.execute();


	        
	        sql = "select cid from cube where pid='"+ cubeId +"'";
	        pst = con.prepareStatement(sql);
	        ResultSet ret = pst.executeQuery();
	        String childId = null;
	        while(ret.next())
	        {
	        	childId = ret.getString(1);
	        	this.deleteCube(childId);
	        }
	        
		    con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}  
	}
	
	public Cube createCube(String cubeType)
	{
		Cube cube = null;
        Class c;
		try {
			c = Class.forName(types.get(cubeType));
	        cube = (Cube)c.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		
		cube.cubeType = cubeType;
		cube.id = UUID.randomUUID().toString();
		
		try 
	    {  
			Connection con = LocalDbConnector.getConnection();
			String sql = "insert into cube(cid,type) values ('" +cube.id + "','"+cube.cubeType + "')" ;
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			
			pst.execute();


		    con.close();  
		    pst.close();  
	        
	    }catch (Exception e) {  
		    e.printStackTrace();  
		}  
		
		return cube;
	}
}
