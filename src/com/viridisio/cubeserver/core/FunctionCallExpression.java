package com.viridisio.cubeserver.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class FunctionCallExpression extends Expression {

	public Expression functionExpression;
	
	public ArrayList<Expression> inputParameters = new ArrayList<Expression>();
	
	public String outputParameterId;
	
	private IExpressionValue result;
	
	@Override
	public void calculate() {
		
		Function fun = null;
		if (this.functionExpression != null)
		{
			this.functionExpression.calculate();
			fun = (Function)this.functionExpression.getResultValue();
		}
			
		ArrayList<IExpressionValue> inputParameterValues = new ArrayList<IExpressionValue>();
		for (Expression exp:this.inputParameters) {
			exp.calculate();
			inputParameterValues.add(exp.getResultValue());
		}
		
		this.result = fun.execute(inputParameterValues,this.outputParameterId);
	}

	@Override
	public IExpressionValue getResultValue() {
		
		return this.result;
	}

	@Override
	public Document toXml() {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement(Expression.FUNCTION_CALL_EXPRESSION); 
		
		
		Element functionExpression = root.addElement("functionExpression"); 
		functionExpression.add(this.functionExpression.toXml().getRootElement());
		
		Element inputPara = null;
		for (Expression para:this.inputParameters)
		{
			inputPara = root.addElement("inputPara"); 
			inputPara.add(para.toXml().getRootElement());
		}
		
		Element outputPara = root.addElement("outputPara"); 
		 outputPara.addText(this.outputParameterId);
		
		return xml;
	}

	@Override
	public void fromXml(Element xml) {
		
		Element functionExpression = xml.element("functionExpression");
		this.functionExpression = Expression.createExpression((Element)functionExpression.elements().get(0));
		
		Document inputParaXml = null;
	    List nodes = xml.elements("inputPara");
	    for (Iterator it = nodes.iterator(); it.hasNext();) {
	    	Element inputPara = (Element) it.next();
	    	this.inputParameters.add(Expression.createExpression((Element)inputPara.elements().get(0)));
	    }
	    
	    Element outputPara = xml.element("outputPara");
	    this.outputParameterId = outputPara.getText();
	}

}
