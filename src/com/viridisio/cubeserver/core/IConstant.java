package com.viridisio.cubeserver.core;

public interface IConstant extends IExpressionValue {

	
	abstract public String saveToString();
	
	abstract public void filledFromString(String content);
}
