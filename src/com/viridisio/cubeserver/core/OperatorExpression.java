package com.viridisio.cubeserver.core;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.gmors.recipe.TempRecipe;

public class OperatorExpression extends Expression {
	
	public String sign;
	
	public Expression leftParameter;
	
	public Expression rightParameter;
	
	private Operator op;
	
	private IExpressionValue result;

	@Override
	public void calculate()  {
		
		if (this.sign == null || this.leftParameter == null || this.rightParameter == null)
			return;

		this.leftParameter.calculate();
		IExpressionValue leftValue = this.leftParameter.getResultValue();
		
		this.rightParameter.calculate();
		IExpressionValue rightValue = this.rightParameter.getResultValue();

		try{
			this.op = OperatorRepository.getInstantce().getOperator(sign, leftValue.getType(), rightValue.getType());
			this.result = this.op.execute(leftValue, rightValue);
		}
		catch(Exception e)
		{
			
		}

	}

	@Override
	public IExpressionValue getResultValue() {
	
		return this.result;
	}

	@Override
	public Document toXml() {
		
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement(Expression.OPERATOR_EXPRESSION); 
		
		Element sign = root.addElement("sign"); 
		sign.addText(this.sign);
		
		Element left = root.addElement("left"); 
		left.add(this.leftParameter.toXml().getRootElement());
		
		Element right = root.addElement("right"); 
		right.add(this.rightParameter.toXml().getRootElement());
		
		return xml;
	}

	@Override
	public void fromXml(Element xml) {
		
		Element sign = xml.element("sign");
		this.sign = sign.getText();
		
		Element left = xml.element("left");
		this.leftParameter = Expression.createExpression((Element)left.elements().get(0));
		
		Element right = xml.element("right");
		this.rightParameter = Expression.createExpression((Element)right.elements().get(0));
	}

}
