package com.viridisio.cubeserver.javafunctioncube;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.viridisio.cubeserver.core.Cube;
import com.viridisio.cubeserver.core.Expression;
import com.viridisio.cubeserver.core.Function;
import com.viridisio.cubeserver.core.IExpressionValue;

public class JavaFunctionCube extends Cube {
	
	public static String CUBE_TYPE = "JavaFunctionCube";

	public String className;
	
	private Function fun;
	
	@Override
	public IExpressionValue getAttributeValueByName(String attributeName) {

		if (!"function".equals(attributeName))
			return null;
		
		if (this.fun == null)
		{
			try {
				Class c= Class.forName(this.className);
				this.fun = (Function)c.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return this.fun;
	}

	@Override
	public IExpressionValue getInternalVariableValueByName(String internalVariableName) {

		return null;
	}

	@Override
	public Document saveToXml() {
		
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("cube");
		Element functionName = root.addElement("functionClassName"); 
		functionName.addText(this.className);
		
		return xml;
	}

	@Override
	public void fillFromXml(Document xml) {
		
		Element root = xml.getRootElement();
		Element functionName = root.element("functionClassName");
		
		this.className = functionName.getText();
		
	}

	@Override
	public void beforeDelete() {
		
	}

	@Override
	public Document doAction(Document action) {
		// TODO Auto-generated method stub
		return null;
	}

}
