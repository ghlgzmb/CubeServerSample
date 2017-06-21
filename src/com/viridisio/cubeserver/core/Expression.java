package com.viridisio.cubeserver.core;

import org.dom4j.Document;
import org.dom4j.Element;

public abstract class Expression {
	
	public static String CUBE_ATTRIBUTE_EXPRESSION = "cube_attribute_expression";
	public static String EXPRESSION_ATTRIBUTE_EXPRESSION = "expression_attribute_expression";
	public static String CUBE_INTERNAL_VARIABLE_EXPRESSION = "cube_internal_varialbe_expression";
	public static String FUNCTION_CALL_EXPRESSION = "function_call_expression";
	public static String CONSTANT_EXPRESSION = "constant_expression";
	public static String OPERATOR_EXPRESSION = "operator_expression";
	
	public static Expression createExpression(Element xml)
	{
		
		String type = xml.getName();
		Expression exp = null;
		
		if (CUBE_ATTRIBUTE_EXPRESSION.equals(type))
		{
			exp = new CubeAttributeExpression();
			exp.fromXml(xml);
		}
		
		if (EXPRESSION_ATTRIBUTE_EXPRESSION.equals(type))
		{
			exp = new ExpressionAttributeExpression();
			exp.fromXml(xml);
		}
		
		if (CUBE_INTERNAL_VARIABLE_EXPRESSION.equals(type))
		{
			exp = new CubeInternalVariableExpression();
			exp.fromXml(xml);
		}
		
		if (FUNCTION_CALL_EXPRESSION.equals(type))
		{
			exp = new FunctionCallExpression();
			exp.fromXml(xml);
		}
		
		if (CONSTANT_EXPRESSION.equals(type))
		{
			exp = new ConstantExpression();
			exp.fromXml(xml);
		}
		
		if (OPERATOR_EXPRESSION.equals(type))
		{
			exp = new OperatorExpression();
			exp.fromXml(xml);
		}
		
		return exp;
	}
	
	abstract public void  calculate();
	
	abstract public IExpressionValue getResultValue();
	
	abstract public Document toXml();
	
	abstract public void fromXml(Element xml);

}
