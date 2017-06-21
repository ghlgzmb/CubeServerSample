package com.viridisio.cubeserver.simplecube;

import java.util.Date;

import com.viridisio.cubeserver.core.IExpressionValue;

public abstract class StringValue implements IExpressionValue {

	public static String EXPRESION_VALUE_TYPE = "string";
	
	public abstract String getString();
	
	@Override
	public String getType() {
	
		return EXPRESION_VALUE_TYPE;
	}

	@Override
	public IExpressionValue getAttributeValue(String attributeName) {

		if ("length".equals(attributeName))
		{
			TempNumber tn = new TempNumber();
			tn.lastModifiedDate = new Date();
			tn.number = this.getString().length();
			return tn;
		}
		
		return null;
	}

}
