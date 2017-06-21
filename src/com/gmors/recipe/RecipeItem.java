package com.gmors.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

public class RecipeItem {

	public String Material;
	
	public double weight;
	
	public RecipeItem parent;
	
	public ArrayList<RecipeItem> children = new ArrayList<RecipeItem>();
	
	public double weight(String Material){
		
		if (Material.equals(this.Material))
			return this.weight;
		else
		{
			double weight = 0;
			for(RecipeItem r : this.children)
			{
				weight += r.weight(Material) * this.weight;
			}
			return weight;
		}
	}
	
	public RecipeItem clone(boolean isDeep)
	{
		RecipeItem item = new RecipeItem();
		item.Material = this.Material;
		item.weight = this.weight;
		
		if (isDeep == false)
			return item;
		
		else
		{
			RecipeItem tmp;
			for (RecipeItem i:this.children)
			{
				tmp = i.clone(true);
				tmp.parent = item;
				item.children.add(tmp);
				
			}
			
			return item;
		}
	}
	
	
	public void toXmlElement(Element root)
	{
		Element e = root.addElement("RecipeItem");
		e.addAttribute("material", this.Material);
		e.addAttribute("weight", Double.toString(this.weight));
		
		for (RecipeItem r : this.children)
			r.toXmlElement(e);
	}
	
	public void fromXmlElement(Element e)
	{
		 this.Material = e.attributeValue("material");
		 this.weight = Double.parseDouble(e.attributeValue("weight"));
		 
		 RecipeItem ri = null;

	     List nodes = e.elements("RecipeItem");
	     for (Iterator it = nodes.iterator(); it.hasNext();) {     
        	 Element elm = (Element) it.next(); 
        	 ri = new RecipeItem();
			 ri.fromXmlElement(elm);
			 this.children.add(ri);
         }  
		 
	}
}
