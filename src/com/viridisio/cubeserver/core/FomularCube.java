package com.viridisio.cubeserver.core;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class FomularCube extends Cube {
	
	public static String CUBE_TYPE = "FomularCube";

	public Expression exp;
	
	@Override
	public IExpressionValue getAttributeValueByName(String attributeName) {
		if (attributeName.equals("value") && this.exp !=null)
		{
			this.exp.calculate();
			return this.exp.getResultValue();
		}
			
		
		return null;
	}

	@Override
	public IExpressionValue getInternalVariableValueByName(String internalVariableName) {

		return null;
	}

	@Override
	public Document saveToXml() {
		
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("cube");
		root.add(this.exp.toXml().getRootElement()); 
		
		return xml;
	}

	@Override
	public void fillFromXml(Document xml) {

		Element root = xml.getRootElement();

		List children = root.elements();
		Element exp = (Element)(children.get(0));

		this.exp = Expression.createExpression(exp);
	}

	@Override
	public void beforeDelete() {
		
	}

}
