package com.viridisio.cubeserver.core;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.Element;

public abstract class Cube {
	
	public String id;
	
	public String cubeType;
	
	public String parent;
	
	public Date lastModifiedTime;
	
	abstract public IExpressionValue getAttributeValueByName(String attributeName);
	
	abstract public IExpressionValue getInternalVariableValueByName(String internalVariableName);
	
	abstract public Document saveToXml();
	
	abstract public void fillFromXml(Document xml);
	
	abstract public void beforeDelete();
	
	abstract public Document doAction(Document action);
}
