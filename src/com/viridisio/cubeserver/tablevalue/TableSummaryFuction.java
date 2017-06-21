package com.viridisio.cubeserver.tablevalue;

import java.util.ArrayList;
import java.util.Date;

import com.viridisio.cubeserver.core.Function;
import com.viridisio.cubeserver.core.FunctionParameterDefinition;
import com.viridisio.cubeserver.core.IExpressionValue;
import com.viridisio.cubeserver.simplecube.NumberValue;
import com.viridisio.cubeserver.simplecube.StringValue;
import com.viridisio.cubeserver.simplecube.TempNumber;

public class TableSummaryFuction extends Function {
	
	public TableValue table;

	public Date lastModifiedDate;
	
	public TableSummaryFuction(TableValue table) {
		super();
		this.lastModifiedDate = new Date();
		this.table = table;
		
		FunctionParameterDefinition fpdef1 = new FunctionParameterDefinition();
		fpdef1.id = "column";
		fpdef1.valueType = StringValue.EXPRESION_VALUE_TYPE;
		this.inputParameterDefinitions.add(fpdef1);
		
		FunctionParameterDefinition fpdef2 = new FunctionParameterDefinition();
		fpdef2.id = "summary";
		fpdef2.valueType = NumberValue.EXPRESION_VALUE_TYPE;
		this.outputParameterDefinitions.add(fpdef2);
	}

	@Override
	public Date getLastModifiedDate() {
		
		return this.lastModifiedDate;
	}

	@Override
	public IExpressionValue execute(ArrayList<IExpressionValue> inputParameterValues, String outputParameterId) {
		
		if (inputParameterValues.size() ==1 && "summary".equals(outputParameterId))
		{
			TempNumber outputValue = new TempNumber();
			outputValue.lastModifiedDate = new Date();
			
			StringValue column = (StringValue) inputParameterValues.get(0);
			String columnName = column.getString();
			
			for (TableColumn col :this.table.getTableColumns())
			{
				if (col.name.equals(columnName) && col.valueType.equals(NumberValue.EXPRESION_VALUE_TYPE))
				{
					NumberValue number;
					for (TableRow row : this.table.getTableRows())
					{
						number = (NumberValue) row.getCell(col);
						outputValue.number += number.getNumber();
					}
				}
			}
			
			return outputValue;
		}
		
		return null;
	}

}
