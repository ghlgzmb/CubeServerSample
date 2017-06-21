package com.viridisio.cubeserver.localtablecube;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.gmors.recipe.RecipeCube;
import com.gmors.recipe.RecipeItem;
import com.gmors.recipe.TempRecipe;
import com.viridisio.cubeserver.core.ConstantFactory;
import com.viridisio.cubeserver.core.Cube;
import com.viridisio.cubeserver.core.CubeRepository;
import com.viridisio.cubeserver.core.IConstant;
import com.viridisio.cubeserver.core.IExpressionValue;
import com.viridisio.cubeserver.simplecube.BooleanValue;
import com.viridisio.cubeserver.simplecube.NumberValue;
import com.viridisio.cubeserver.simplecube.StringValue;
import com.viridisio.cubeserver.simplecube.TempBoolean;
import com.viridisio.cubeserver.simplecube.TempNumber;
import com.viridisio.cubeserver.simplecube.TempString;
import com.viridisio.cubeserver.utility.LocalDbConnector;


public class LocalTableCube extends Cube {
	
	public static String CUBE_TYPE = "LocalTableCube";

	public ArrayList<LocalTableRow> rows = new ArrayList<LocalTableRow >();
	
	public ArrayList<LocalTableColumn> columns = new ArrayList<LocalTableColumn>();
	
	public String currentRowId;
	
	private String dbTableName;

	@Override
	public IExpressionValue getAttributeValueByName(String attributeName) {
		
		if ("table".equals(attributeName))
		{
			LocalTable table = new LocalTable();
			table.cube = this;
			return table;
		}
		else return null;
	}

	@Override
	public IExpressionValue getInternalVariableValueByName(String internalVariableName) {
		
		String colName = (internalVariableName.split("\\."))[0];
		LocalTableColumn col = null;
		for (LocalTableColumn c:this.columns)
		{
			if (colName.equals(c.name))
				col = c;
		}
		
		LocalTableRow row = null;

		for (LocalTableRow r: this.rows)
		{
			if (r.rowID.equals(this.currentRowId))
				row = r;
		}
		
		if (col.isCube == true)
		{
			String cubeAttributeName = (internalVariableName.split("\\."))[1];
			Cube c = (Cube)row.cells.get(col);
			IExpressionValue value = c.getAttributeValueByName(cubeAttributeName);
			return value;
		}
		else return (IExpressionValue)row.getCell(col);

	}

	@Override
	public Document saveToXml() {
		
		Connection con = LocalDbConnector.getConnection();
		String sql = null;
		PreparedStatement pst = null;  
		try 
	    {  
			if (this.dbTableName == null)
			{
				this.dbTableName = "tab_"+this.id.replace("-", "_");
				sql = "create table "+ this.dbTableName + " ( rowid VARCHAR(36),";
				for (LocalTableColumn col:this.columns)
				{
					sql = sql  + col.name +" VARCHAR(255),";
				}
				sql +=  "PRIMARY KEY (rowid))";
				pst = con.prepareStatement(sql);
				pst.execute();
				
				sql = "truncate "+ this.dbTableName;
				pst = con.prepareStatement(sql);
				pst.execute();
			}
		
			for (LocalTableRow row:this.rows)
			{
				sql = "insert into "+ this.dbTableName + " (rowid";
				
				for (LocalTableColumn col:this.columns)
				{
					sql = sql +  "," + col.name ;
				}
				sql = sql + ") values ( '" + row.rowID + "'" ;
				
				Object cell = null;
				for (LocalTableColumn col:this.columns)
				{
					cell = row.cells.get(col);
					if (col.isCube == true)
						sql = sql + ", '" + ((Cube)cell).id + "'" ;
					else sql = sql +  ", '" + ((IConstant)cell).saveToString() + "'";
				}
				
				sql = sql + ")";
				pst = con.prepareStatement(sql);
				pst.execute();
			}
		
			con.close();  
			pst.close(); 
			
		
		}catch (Exception e){  
				e.printStackTrace();  
		}  
		
		Document cubeXml = DocumentHelper.createDocument();
		Element root = cubeXml.addElement("cube");
		
		Element tableName = root.addElement("tableName");
		tableName.setText(this.dbTableName);
		
		Element column = null;
		for (LocalTableColumn col:this.columns)
		{
			column = root.addElement("column");
			column.addAttribute("name", col.name);
			column.addAttribute("valueType", col.valueType);
			if (col.isCube == true)
				column.addAttribute("isCube", "true");
			else column.addAttribute("isCube", "false");
			if (col.isFomular == true)
				column.addAttribute("isFomular", "true");
			else column.addAttribute("isFomular", "false");
		}
		
		
		return cubeXml;

	}

	@Override
	public void fillFromXml(Document xml) {
		
		Element root = xml.getRootElement();
		
		Element tableName = root.element("tableName");
		this.dbTableName = tableName.getText();
		
		LocalTableColumn col;
	    List nodes = root.elements("column");
	    for (Iterator it = nodes.iterator(); it.hasNext();) 
	    {     
	    	Element elm = (Element) it.next(); 
       	 	col = new LocalTableColumn();
       	 	col.name = elm.attributeValue("name");
       	 	col.valueType = elm.attributeValue("valueType");
       	 	if ("true".equals(elm.attributeValue("isCube")))
       	 		col.isCube = true;
       	 	else col.isCube = false;
       	 	if ("true".equals(elm.attributeValue("isFomular")))
       	 		col.isFomular = true;
       	 	else col.isFomular = false;
       	 	
       	 	this.columns.add(col);
        }  
	    

		try 
	    {  
			Connection con = LocalDbConnector.getConnection();
			String sql = "select * from "+ this.dbTableName;
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			ResultSet ret = pst.executeQuery();
			
			LocalTableRow row = null;
			int colIndex = 0;
	        while(ret.next())
	        {
	        	row = new LocalTableRow();
	        	row.cube = this;
	        	row.rowID = ret.getString(1);
	        	colIndex = 0;
	        	for (;colIndex < this.columns.size();colIndex ++)
	    		{
	        		if (this.columns.get(colIndex).isCube == true)
	        			row.cells.put(this.columns.get(colIndex), CubeRepository.getInstance().getCube(ret.getString(colIndex+2)));
	        		else row.cells.put(this.columns.get(colIndex), ConstantFactory.createConstant(
	        				this.columns.get(colIndex).valueType, ret.getString(colIndex+2)));
	    		}
        		this.rows.add(row);
	        }
	    	 
			con.close();  
			pst.close(); 
		
	    }catch (Exception e){  
			e.printStackTrace();  
	    }  
	}

	@Override
	public void beforeDelete() {
		
		if (this.dbTableName == null)
			return ;
		
		try 
	    {  
			Connection con = LocalDbConnector.getConnection();
			String sql = "drop table "+ this.dbTableName;
			PreparedStatement pst = null;  
			pst = con.prepareStatement(sql);
			pst.execute();
			
			con.close();  
			pst.close(); 
	    }catch (Exception e){  
			e.printStackTrace();  
	    }  
		
	}

}
