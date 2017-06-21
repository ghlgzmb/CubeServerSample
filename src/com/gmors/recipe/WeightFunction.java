package com.gmors.recipe;

import java.util.ArrayList;
import java.util.Date;

import com.viridisio.cubeserver.core.Function;
import com.viridisio.cubeserver.core.FunctionParameterDefinition;
import com.viridisio.cubeserver.core.IExpressionValue;
import com.viridisio.cubeserver.simplecube.NumberValue;
import com.viridisio.cubeserver.simplecube.StringValue;
import com.viridisio.cubeserver.simplecube.TempNumber;

public class WeightFunction extends Function {
	
	public RecipeValue recipe;

	public Date lastModifiedDate;
	
	public WeightFunction(RecipeValue recipe) {
		super();
		this.lastModifiedDate = new Date();
		this.recipe = recipe;
		
		FunctionParameterDefinition fpdef1 = new FunctionParameterDefinition();
		fpdef1.id = "material";
		fpdef1.valueType = NumberValue.EXPRESION_VALUE_TYPE;
		this.inputParameterDefinitions.add(fpdef1);
		
		FunctionParameterDefinition fpdef2 = new FunctionParameterDefinition();
		fpdef2.id = "weight";
		fpdef2.valueType = NumberValue.EXPRESION_VALUE_TYPE;
		this.outputParameterDefinitions.add(fpdef2);
	}

	@Override
	public Date getLastModifiedDate() {

		return this.lastModifiedDate;
	}

	@Override
	public IExpressionValue execute(ArrayList<IExpressionValue> inputParameterValues, String outputParameterId) {
		
		if (inputParameterValues.size() ==1 && "weight".equals(outputParameterId))
		{
			StringValue inputValue1 = (StringValue)inputParameterValues.get(0);
			
			TempNumber outputValue = new TempNumber();
			outputValue.lastModifiedDate = new Date();
			outputValue.number = this.recipe.root.weight(inputValue1.getString());
			
			return outputValue;
		}
		
		return null;
	}

}
