package com.viridisio.cubeserver.core;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ExpressionAttributeExpression extends Expression {

	public Expression expression;
	
	public String attributeName;
	
	public Date cubeLastModifiedDate;
	
	public IExpressionValue result;
	
	@Override
	public void calculate() {
		
		if (this.expression == null || this.attributeName == null)
			return ;
		
		this.expression.calculate();
		IExpressionValue currentValue = this.expression.getResultValue();

		this.result = currentValue.getAttributeValue(this.attributeName);
		this.cubeLastModifiedDate = currentValue.getLastModifiedDate();


	}

	@Override
	public IExpressionValue getResultValue() {
		
		return this.result;
	}

	@Override
	public Document toXml() {
		
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement(Expression.EXPRESSION_ATTRIBUTE_EXPRESSION); 
		
		Element attributeName = root.addElement("attributeName"); 
		attributeName.addText(this.attributeName);
		
		Element expression = root.addElement("expression"); 
		expression.add(this.expression.toXml().getRootElement());
			
		return xml;
	}

	@Override
	public void fromXml(Element xml) {
		
		Element attributeName = xml.element("attributeName");
		this.attributeName = attributeName.getText();
		
		Element expression = xml.element("expression");
		this.expression = Expression.createExpression((Element)expression.elements().get(0));
	}

}
