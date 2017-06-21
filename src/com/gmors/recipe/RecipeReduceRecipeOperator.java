package com.gmors.recipe;

import java.util.Date;

import com.viridisio.cubeserver.core.IExpressionValue;
import com.viridisio.cubeserver.core.Operator;

public class RecipeReduceRecipeOperator extends Operator {

	@Override
	public IExpressionValue execute(IExpressionValue leftValue, IExpressionValue rightValue) {

		if (leftValue != null && rightValue != null)
		{
			RecipeValue oldRecipe = (RecipeValue)leftValue;
			RecipeValue replaceRecipe = (RecipeValue)rightValue;
			
			TempRecipe newRecipe = new TempRecipe();
			newRecipe.lastModifiedDate = new Date();
			newRecipe.root = replace(oldRecipe.root,replaceRecipe.root);
			return newRecipe;
		}
		
		return null;
	}
	
	public RecipeItem replace(RecipeItem oldRecipe ,RecipeItem replaceRecipe)
	{
		RecipeItem  ri = null;
		if (oldRecipe.Material.equals(replaceRecipe.Material))
		{
			ri = replaceRecipe.clone(true);
			ri.weight = oldRecipe.weight;
		}
		else
		{
			ri = oldRecipe.clone(false);
			for (RecipeItem i:oldRecipe.children)
			{
				ri.children.add(replace(i,replaceRecipe));				
			}
		}
		return ri;
			
	}

}
