package com.viridisio.cubeserver.core;

import java.util.Date;

public interface IExpressionValue {

	public String getType();
	
	public Date getLastModifiedDate();
	
	public IExpressionValue getAttributeValue(String attributeName);
}
