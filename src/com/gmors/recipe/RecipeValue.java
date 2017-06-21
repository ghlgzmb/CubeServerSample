package com.gmors.recipe;

import com.viridisio.cubeserver.core.IExpressionValue;

public abstract class RecipeValue implements IExpressionValue {

	public static String EXPRESION_VALUE_TYPE = "recipe";
	
	public RecipeItem root;
	
	@Override
	public String getType() {
		
		return EXPRESION_VALUE_TYPE;
	}

	@Override
	public IExpressionValue getAttributeValue(String attributeName) {
		
		if ("weight".equals(attributeName))
		{
			WeightFunction wf = new WeightFunction(this);
			return wf;
		}
		
			
	    return null;
	}
	
	abstract public double weight(String material);

}
