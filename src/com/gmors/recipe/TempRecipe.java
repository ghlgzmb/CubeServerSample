package com.gmors.recipe;

import java.util.Date;

public class TempRecipe extends RecipeValue {

	public Date lastModifiedDate;
	
	
	public TempRecipe() {
		super();
		this.lastModifiedDate = new Date();
	}

	@Override
	public Date getLastModifiedDate() {
		
		return this.lastModifiedDate;
	}

	@Override
	public double weight(String material) {
		
		if (this.root == null)
			return 0;
		
		return this.root.weight(material);
	}

}
