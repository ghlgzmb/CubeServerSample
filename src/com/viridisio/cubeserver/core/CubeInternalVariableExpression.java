package com.viridisio.cubeserver.core;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CubeInternalVariableExpression extends Expression {
	
	public String cubeID;
	
	public String internalVariableName;
	
	public Date cubeLastModifiedDate;
	
	public IExpressionValue result;
	
	@Override
	public void calculate() {
		
		Cube cube = CubeRepository.getInstance().getCube(this.cubeID);
		if (cube == null || this.internalVariableName == null)
			return ;
		
		this.result = cube.getInternalVariableValueByName(this.internalVariableName);
		this.cubeLastModifiedDate = cube.lastModifiedTime;

	}

	@Override
	public IExpressionValue getResultValue() {
		
		return this.result;
	}

	@Override
	public Document toXml() {
		
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement(Expression.CUBE_INTERNAL_VARIABLE_EXPRESSION); 
		
		Element internalVariableName = root.addElement("internalVariableName"); 
		internalVariableName.addText(this.internalVariableName);
		
		Element cubeId = root.addElement("cubeId"); 
		cubeId.addText(this.cubeID);
			
		return xml;
	}

	@Override
	public void fromXml(Element xml) {
		
		
		Element internalVariableName = xml.element("internalVariableName");
		this.internalVariableName = internalVariableName.getText();
		
		Element cubeId = xml.element("cubeId");
		this.cubeID = cubeId.getText();
	}
}
