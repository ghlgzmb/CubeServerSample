package com.viridisio.cubeserver.core;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CubeAttributeExpression extends Expression {

	public String cubeID;
	
	public String attributeName;
	
	public Date cubeLastModifiedDate;
	
	public IExpressionValue result;
	
	@Override
	public void calculate() {
		
		Cube cube = CubeRepository.getInstance().getCube(this.cubeID);
		
		if (cube == null || this.attributeName == null)
			return ;
		

		this.result = cube.getAttributeValueByName(this.attributeName);
		this.cubeLastModifiedDate = cube.lastModifiedTime;


	}

	@Override
	public IExpressionValue getResultValue() {
		
		return this.result;
	}

	@Override
	public Document toXml() {

		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement(Expression.CUBE_ATTRIBUTE_EXPRESSION); 
		
		Element attributeName = root.addElement("attributeName"); 
		attributeName.addText(this.attributeName);
		
		Element cubeId = root.addElement("cubeId"); 
		cubeId.addText(this.cubeID);
			
		return xml;
	}

	@Override
	public void fromXml(Element xml) {
		
		Element attributeName = xml.element("attributeName");
		this.attributeName = attributeName.getText();
		
		Element cubeId = xml.element("cubeId");
		this.cubeID = cubeId.getText();
	}

}
