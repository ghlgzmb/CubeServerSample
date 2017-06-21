package com.viridisio.cubeserver.javafunctioncube;

import java.util.ArrayList;
import java.util.Date;

import com.viridisio.cubeserver.core.Function;
import com.viridisio.cubeserver.core.FunctionParameterDefinition;
import com.viridisio.cubeserver.core.IExpressionValue;
import com.viridisio.cubeserver.simplecube.NumberValue;
import com.viridisio.cubeserver.simplecube.StringValue;
import com.viridisio.cubeserver.simplecube.TempNumber;

public class CurrencyExchangeFunction extends Function {

	public Date lastModifiedDate;
	
	public CurrencyExchangeFunction() {
		super();
		this.lastModifiedDate = new Date();
		
		FunctionParameterDefinition fpdef1 = new FunctionParameterDefinition();
		fpdef1.id = "fromCurrency";
		fpdef1.valueType = StringValue.EXPRESION_VALUE_TYPE;
		this.inputParameterDefinitions.add(fpdef1);
		
		FunctionParameterDefinition fpdef2 = new FunctionParameterDefinition();
		fpdef2.id = "toCurrency";
		fpdef2.valueType = StringValue.EXPRESION_VALUE_TYPE;
		this.inputParameterDefinitions.add(fpdef2);
		
		FunctionParameterDefinition fpdef3 = new FunctionParameterDefinition();
		fpdef3.id = "fromNumber";
		fpdef3.valueType = NumberValue.EXPRESION_VALUE_TYPE;
		this.inputParameterDefinitions.add(fpdef3);
		
		FunctionParameterDefinition fpdef4 = new FunctionParameterDefinition();
		fpdef4.id = "toNumber";
		fpdef4.valueType = NumberValue.EXPRESION_VALUE_TYPE;
		this.outputParameterDefinitions.add(fpdef4);
	}

	@Override
	public Date getLastModifiedDate() {

		return this.lastModifiedDate;
	}

	@Override
	public IExpressionValue execute(ArrayList<IExpressionValue> inputParameterValues, String outputParameterId) {
		
		if (inputParameterValues.size() ==3 && "toNumber".equals(outputParameterId))
		{
			StringValue inputValue1 = (StringValue)inputParameterValues.get(0);
			StringValue inputValue2 = (StringValue)inputParameterValues.get(1);
			NumberValue inputValue3 = (NumberValue)inputParameterValues.get(2);
			
			if ("USD".equals(inputValue1.getString()) && "TWD".equals(inputValue2.getString()))
			{
				TempNumber outputValue = new TempNumber();
				outputValue.lastModifiedDate = new Date();
				outputValue.number = inputValue3.getNumber() *30;
				return outputValue;
			}
			
			if ("TWD".equals(inputValue1.getString()) && "USD".equals(inputValue2.getString()))
			{
				TempNumber outputValue = new TempNumber();
				outputValue.lastModifiedDate = new Date();
				outputValue.number = inputValue3.getNumber() /30;
				return outputValue;
			}
		}
		
		return null;
	}

}
