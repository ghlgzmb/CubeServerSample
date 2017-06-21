package com.viridisio.cubeserver.simplecube;

import com.viridisio.cubeserver.core.IExpressionValue;

public abstract class NumberValue implements IExpressionValue {

	public static String EXPRESION_VALUE_TYPE = "number";
	
	public abstract double getNumber();
	
	@Override
	public String getType() {

		return EXPRESION_VALUE_TYPE;
	}

	@Override
	public IExpressionValue getAttributeValue(String attributeName) {

		return null;
	}

}
