package com.viridisio.cubeserver.core;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ConstantExpression extends Expression {

	public IConstant constant;
	
	@Override
	public void calculate() {

	}

	@Override
	public IExpressionValue getResultValue() {
		
		return this.constant;
	}

	@Override
	public Document toXml() {
		
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement(Expression.CONSTANT_EXPRESSION); 
		
		Element type = root.addElement("type"); 
		type.addText(this.constant.getType());
		
		Element content = root.addElement("content"); 
		content.addText(this.constant.saveToString());
		
		return xml;
	}

	@Override
	public void fromXml(Element xml) {

		
		Element type = xml.element("type");
		Element content = xml.element("content");
		
		this.constant = ConstantFactory.createConstant(type.getText(), content.getText());
	}

}
