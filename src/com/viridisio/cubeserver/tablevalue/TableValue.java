package com.viridisio.cubeserver.tablevalue;

import java.util.ArrayList;

import com.viridisio.cubeserver.core.IExpressionValue;

public abstract class TableValue implements IExpressionValue {

	public static String EXPRESION_VALUE_TYPE = "expression value type :  table";
	
	@Override
	public String getType() {
		
		return EXPRESION_VALUE_TYPE;
	}

	@Override
	public IExpressionValue getAttributeValue(String attributeName) {

		if ("summary".equals(attributeName))
		{
			TableSummaryFuction function = new TableSummaryFuction(this);
			return  function;
		}
		
		return null;
	}
	
	abstract public ArrayList<TableRow> getTableRows();
	
	abstract public ArrayList<TableColumn> getTableColumns();

}
