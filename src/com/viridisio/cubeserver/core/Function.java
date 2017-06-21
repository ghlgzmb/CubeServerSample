package com.viridisio.cubeserver.core;

import java.util.ArrayList;

public abstract class Function implements IExpressionValue {
	
	public static String EXPRESSION_VALUE_TYPE_FUNCTION = "function";
	
	public ArrayList<FunctionParameterDefinition> inputParameterDefinitions = new ArrayList<FunctionParameterDefinition>();
	
	public ArrayList<FunctionParameterDefinition> outputParameterDefinitions = new ArrayList<FunctionParameterDefinition>();
	
	@Override
	public String getType() {

		return EXPRESSION_VALUE_TYPE_FUNCTION;
	}

	@Override
	public IExpressionValue getAttributeValue(String attributeName) {

		return null;
	}
	
	public abstract IExpressionValue execute(ArrayList<IExpressionValue> inputParameterValues, String outputParameterId) ;

}
