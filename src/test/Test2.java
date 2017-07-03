package test;

import com.gmors.recipe.*;
import com.viridisio.cubeserver.core.ConstantExpression;
import com.viridisio.cubeserver.core.CubeAttributeExpression;
import com.viridisio.cubeserver.core.CubeRepository;
import com.viridisio.cubeserver.core.Expression;
import com.viridisio.cubeserver.core.ExpressionAttributeExpression;
import com.viridisio.cubeserver.core.FomularCube;
import com.viridisio.cubeserver.core.FunctionCallExpression;
import com.viridisio.cubeserver.core.OperatorExpression;
import com.viridisio.cubeserver.javafunctioncube.JavaFunctionCube;
import com.viridisio.cubeserver.simplecube.NumberValue;
import com.viridisio.cubeserver.simplecube.TempString;

public class Test2 {
	
	public static CubeRepository cr = CubeRepository.getInstance();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//create();
		

		RecipeCube cube1 = (RecipeCube) cr.getCube("dea29881-a94a-41e6-a552-b7f2012cb1ea");


		
		ConstantExpression c = new ConstantExpression();
		TempString ts = new TempString();
		ts.str = "M3";
		c.constant = ts;
		
		// cube1.recipe.weight("M3").weight;
		FunctionCallExpression exp = new FunctionCallExpression();
		exp.outputParameterId = "weight";
		exp.inputParameters.add(c);
		CubeAttributeExpression e = new CubeAttributeExpression();
		e.attributeName = "recipe";
		e.cubeID = cube1.id;
		ExpressionAttributeExpression a = new ExpressionAttributeExpression();
		a.attributeName = "weight";
		a.expression = e;
		exp.functionExpression = a;
		
		exp.calculate();
		NumberValue w = (NumberValue)exp.getResultValue();
		System.out.println("M1 has M3 weight : " +w.getNumber());
		
		//cube1.recipe.weight("M4");
		ts.str = "M4";
		exp.calculate();
		w = (NumberValue)exp.getResultValue();
		System.out.println("M1 has M4 weight : "+w.getNumber());

		//cube1.recipe.weight("M4");
		ts.str = "M5";
		exp.calculate();
		w = (NumberValue)exp.getResultValue();
		System.out.println("M1 has M5 weight : " +w.getNumber());
		
	
		
		//cube3
		FomularCube cube3 = (FomularCube) cr.getCube("6d9c4423-88e1-4bab-ad1f-8c4b3188cae7");
		ConstantExpression c1 = new ConstantExpression();
		TempString ts1 = new TempString();
		ts1.str = "M3";
		c1.constant = ts1;
		
		
		//cube3.value.weight("M3");
		FunctionCallExpression exp1 = new FunctionCallExpression();
		exp1.outputParameterId = "weight";
		exp1.inputParameters.add(c1);
		CubeAttributeExpression e1 = new CubeAttributeExpression();
		e1.attributeName = "value";
		e1.cubeID = cube3.id;
		ExpressionAttributeExpression a1 = new ExpressionAttributeExpression();
		a1.attributeName = "weight";
		a1.expression = e1;
		exp1.functionExpression = a1;
		
		exp1.calculate();
		NumberValue w1 = (NumberValue)exp1.getResultValue();
		System.out.println("After repaced M2, M1 has M3 weight : " +w1.getNumber());
		
		//cube3.value.weight("M4");
		ts1.str = "M4";
		exp1.calculate();
		w1 = (NumberValue)exp1.getResultValue();
		System.out.println("After repaced M2, M1 has M3 weight : "+w1.getNumber());

		//cube3.value.weight("M5");
		ts1.str = "M5";
		exp1.calculate();
		w1 = (NumberValue)exp1.getResultValue();
		System.out.println("After repaced M2, M1 has M3 weight : "+w1.getNumber());

	}

	public static void create()
	{
		TempRecipe tr ;
		RecipeItem ri1 ,ri2,ri3,ri4,ri5;
		
		//cube1   recipe M1
		RecipeCube cube1 = (RecipeCube) cr.createCube(RecipeCube.CUBE_TYPE);
		
		tr = new TempRecipe();
		
		ri1 = new RecipeItem();
		ri1.Material = "M1";
		ri1.weight = 1;
		tr.root = ri1;
		
		ri2 = new RecipeItem();
		ri2.Material = "M2";
		ri2.weight = 2;
		ri2.parent = ri1;
		ri1.children.add(ri2);
				
		ri5 = new RecipeItem();
		ri5.Material = "M5";
		ri5.weight = 4;
		ri5.parent = ri1;
		ri1.children.add(ri5);
		
		ri3 = new RecipeItem();
		ri3.Material = "M3";
		ri3.weight = 2;
		ri3.parent = ri2;
		ri2.children.add(ri3);
		
		ri4 = new RecipeItem();
		ri4.Material = "M4";
		ri4.weight = 3;
		ri4.parent = ri4;
		ri2.children.add(ri4);
		
		
		cube1.content = tr;
		cr.saveCube(cube1);
		
		
		
		//cube2   new recipe M2
		RecipeCube cube2 = (RecipeCube) cr.createCube(RecipeCube.CUBE_TYPE);
		
		tr = new TempRecipe();
		
		ri2 = new RecipeItem();
		ri2.Material = "M2";
		ri2.weight = 1;
		tr.root = ri2;
		
		ri3 = new RecipeItem();
		ri3.Material = "M3";
		ri3.weight = 1;
		ri3.parent = ri2;
		ri2.children.add(ri3);
		
		ri4 = new RecipeItem();
		ri4.Material = "M4";
		ri4.weight = 5;
		ri4.parent = ri4;
		ri2.children.add(ri4);
		
		cube2.content = tr;
		cr.saveCube(cube2);
			
		
		// cube1.recipe 
		CubeAttributeExpression c1r = new CubeAttributeExpression();
		c1r.attributeName = "recipe";
		c1r.cubeID = cube1.id;
				
		// cube2.recipe
		CubeAttributeExpression c2r = new CubeAttributeExpression();
		c2r.attributeName = "recipe";
		c2r.cubeID = cube2.id;
		
		// cube3 = cube1.recipe - cube2.recipe
		OperatorExpression ope = new OperatorExpression();
		ope.sign = "-";
		ope.leftParameter = c1r;
		ope.rightParameter = c2r;
		
		FomularCube cube3 = (FomularCube)cr.createCube(FomularCube.CUBE_TYPE);
		cube3.exp = ope;
		cr.saveCube(cube3);		
		
	}
	
}
