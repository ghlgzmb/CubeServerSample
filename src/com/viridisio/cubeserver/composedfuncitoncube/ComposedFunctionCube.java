package com.viridisio.cubeserver.composedfuncitoncube;


import java.util.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.viridisio.cubeserver.core.Cube;
import com.viridisio.cubeserver.core.Expression;
import com.viridisio.cubeserver.core.FunctionParameterDefinition;
import com.viridisio.cubeserver.core.IExpressionValue;

public class ComposedFunctionCube extends Cube {
	
	public static String CUBE_TYPE = "ComposedFunctionCube";

	public ArrayList<FunctionParameterDefinition> inputParameterDefinitions = new ArrayList<FunctionParameterDefinition>();
	
	public ArrayList<FunctionParameterDefinition> outputParameterDefinitions = new ArrayList<FunctionParameterDefinition>();
	
	public Map<FunctionParameterDefinition,Expression> outputExpressions = new HashMap<FunctionParameterDefinition,Expression>();
	
	public Map<FunctionParameterDefinition,IExpressionValue> inputExpressionValues = new HashMap<FunctionParameterDefinition,IExpressionValue>();
	
	@Override
	public IExpressionValue getAttributeValueByName(String attributeName) {

		if ("function".equals(attributeName))
		{
			ComposedFunction function = new ComposedFunction(this);
			return function;
		}
		
		return null;
	}

	@Override
	public IExpressionValue getInternalVariableValueByName(String internalVariableName) {

		for (FunctionParameterDefinition fpdf: this.inputParameterDefinitions)
		{
			if (internalVariableName.equals(fpdf.id))
			{
				return this.inputExpressionValues.get(fpdf);
			}
		}
		
		return null;
	}

	@Override
	public Document saveToXml() {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("Cube");
			
		Element e;
		for (FunctionParameterDefinition fpdf: this.inputParameterDefinitions)
		{
			e = root.addElement("inputPara");
			e.addAttribute("id", fpdf.id);
			e.addAttribute("valueType", fpdf.valueType);
		}
		
		for (FunctionParameterDefinition fpdf1: this.outputParameterDefinitions)
		{
			e = root.addElement("outputPara");
			e.addAttribute("id", fpdf1.id);
			e.addAttribute("valueType", fpdf1.valueType);
			Expression exp = this.outputExpressions.get(fpdf1);
			if (exp != null)
				e.add(exp.toXml().getRootElement());
		}
		
		return xml;
	}

	@Override
	public void fillFromXml(Document xml) {
		
		Element root = xml.getRootElement();
		
		FunctionParameterDefinition fpdf;
		
	    List nodes = root.elements("inputPara");
	    for (Iterator it = nodes.iterator(); it.hasNext();) {
	    	Element inputPara = (Element) it.next();
	    	fpdf = new FunctionParameterDefinition();
	    	fpdf.id = inputPara.attributeValue("id");
	    	fpdf.valueType = inputPara.attributeValue("valueType");
	    	this.inputParameterDefinitions.add(fpdf);
	    }
	    
	    nodes = root.elements("outputPara");
	    for (Iterator it = nodes.iterator(); it.hasNext();) {
	    	Element outputPara = (Element) it.next();
	    	fpdf = new FunctionParameterDefinition();
	    	fpdf.id = outputPara.attributeValue("id");
	    	fpdf.valueType = outputPara.attributeValue("valueType");
	    	this.outputParameterDefinitions.add(fpdf);
	    	Expression exp = Expression.createExpression((Element) (outputPara.elements().get(0)));
	    	this.outputExpressions.put(fpdf, exp);
	    }
	}

	@Override
	public void beforeDelete() {
		
	}

}
