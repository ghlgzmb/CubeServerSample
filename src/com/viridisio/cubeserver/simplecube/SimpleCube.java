package com.viridisio.cubeserver.simplecube;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.viridisio.cubeserver.core.Cube;
import com.viridisio.cubeserver.core.IExpressionValue;

public class SimpleCube extends Cube {
	
	public static String CUBE_TYPE = "SimpleCube";
	
	public static String TYPE_NUMBER = "number";
	public static String TYPE_BOOLEAN = "boolean";
	public static String TYPE_STRING = "string";
	
	public String content;
	
	public String contentType;

	@Override
	public IExpressionValue getAttributeValueByName(String attributeName) {
		
		if ("value".equals(attributeName))
		{
			if (this.contentType.equals(TYPE_NUMBER))
			{
				TempNumber tn = new TempNumber();
				tn.lastModifiedDate = new Date();
				tn.number = Double.parseDouble(this.content);
				return tn;
			}
			
			if (this.contentType.equals(TYPE_BOOLEAN))
			{
				TempBoolean tb = new TempBoolean();
				tb.lastModifiedDate = new Date();
				if (this.content.equals("x"))
					tb.bool = true;
				else tb.bool = false;
				return tb;
			}
			
			if (this.contentType.equals(TYPE_STRING))
			{
				TempString ts = new TempString();
				ts.lastModifiedDate = new Date();
				ts.str = this.content;
				return ts;
			}

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
		Element content = root.addElement("content"); 
		Element contentType = root.addElement("contentType"); 
		
		if (this.content != null && this.contentType != null)
		{
			content.addText(this.content);
			contentType.addText(this.contentType);
		}
		
		return xml;
	}

	@Override
	public void fillFromXml(Document xml) {
		
		Element root = xml.getRootElement(); 
		Element content = root.element("content");
		Element contentType = root.element("contentType"); 
		
		this.content = content.getText();
		this.contentType = contentType.getText();
		
	}

	@Override
	public void beforeDelete() {

	}

}
