package com.gmors.recipe;

import java.util.UUID;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.viridisio.cubeserver.core.Cube;
import com.viridisio.cubeserver.core.IExpressionValue;
import com.viridisio.cubeserver.utility.LocalXmlRepository;

public class RecipeCube extends Cube {
	
	public static String CUBE_TYPE = "RecipeCube";

	public TempRecipe content;
	
	public String contentXmlId;
	
	@Override
	public IExpressionValue getAttributeValueByName(String attributeName) {
		
		if ("recipe".equals(attributeName))
			return this.content;
		else return null;
	}

	@Override
	public IExpressionValue getInternalVariableValueByName(String internalVariableName) {

		return null;
	}

	@Override
	public Document saveToXml() {
		
		Document cubeXml = DocumentHelper.createDocument();
		Element root = cubeXml.addElement("cube");
		
		if (this.contentXmlId == null)
		{
			 this.contentXmlId = LocalXmlRepository.createXml(this.contentToXml());
		}
		else 
		{
			LocalXmlRepository.updateXml(this.contentXmlId, this.contentToXml());
		}
		
		Element contentId = root.addElement("contentId");
		contentId.setText(this.contentXmlId);
		
		return cubeXml;
	}

	@Override
	public void fillFromXml(Document xml) {
		
		Element root = xml.getRootElement(); 
		Element contentId = root.element("contentId");
		this.contentXmlId = contentId.getText();
		
		this.xmlToContent();
		
	}

	@Override
	public void beforeDelete() {
		
		if (this.contentXmlId != null)
			LocalXmlRepository.deleteXml(this.contentXmlId);		
	}
	
	private Document contentToXml()
	{
		Document contentXml = DocumentHelper.createDocument();
		Element root = contentXml.addElement("Recipe");
		
		this.content.root.toXmlElement(root);
		
		return contentXml;
	}
	
	private void xmlToContent()
	{
		if (this.contentXmlId == null)
			return;
		
		Document xml = LocalXmlRepository.getXml(this.contentXmlId);
		Element root = xml.getRootElement();
		//System.out.println(xml.asXML());
		
		TempRecipe tr = new TempRecipe();
		tr.root = new RecipeItem();
		
		tr.root.fromXmlElement(root.element("RecipeItem"));
		this.content = tr;
	}

}
