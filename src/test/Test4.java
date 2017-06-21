package test;

import java.util.ArrayList;
import java.util.UUID;

import com.gmors.recipe.RecipeCube;
import com.gmors.recipe.RecipeItem;
import com.gmors.recipe.RecipeValue;
import com.gmors.recipe.TempRecipe;
import com.viridisio.cubeserver.composedfuncitoncube.ComposedFunctionCube;
import com.viridisio.cubeserver.core.ConstantExpression;
import com.viridisio.cubeserver.core.CubeAttributeExpression;
import com.viridisio.cubeserver.core.CubeInternalVariableExpression;
import com.viridisio.cubeserver.core.CubeRepository;
import com.viridisio.cubeserver.core.ExpressionAttributeExpression;
import com.viridisio.cubeserver.core.FomularCube;
import com.viridisio.cubeserver.core.Function;
import com.viridisio.cubeserver.core.FunctionCallExpression;
import com.viridisio.cubeserver.core.FunctionParameterDefinition;
import com.viridisio.cubeserver.core.IExpressionValue;
import com.viridisio.cubeserver.core.OperatorExpression;
import com.viridisio.cubeserver.localtablecube.LocalTableColumn;
import com.viridisio.cubeserver.localtablecube.LocalTableCube;
import com.viridisio.cubeserver.localtablecube.LocalTableRow;
import com.viridisio.cubeserver.simplecube.NumberValue;
import com.viridisio.cubeserver.simplecube.SimpleCube;
import com.viridisio.cubeserver.simplecube.StringValue;
import com.viridisio.cubeserver.simplecube.TempNumber;
import com.viridisio.cubeserver.simplecube.TempString;

public class Test4 {

	public static CubeRepository cr = CubeRepository.getInstance();
	
	public static void main(String[] args) {
		//create();
		
		//cr.deleteCube("5346841e-c11f-48e5-81dd-fb81fd8f4224");
		
/*
		// after replaced by cube2 , printe M1 M3 summary weight
		System.out.println("cube2 recipe changed to M2 = 1*M1 + 5*M3!");
		ComposedFunctionCube cube1 = (ComposedFunctionCube) cr.getCube("5346841e-c11f-48e5-81dd-fb81fd8f4224");
		
		Function f = (Function) cube1.getAttributeValueByName("function");
		TempString ts = new TempString();
		ts.str = "M1";
		ArrayList<IExpressionValue> inputs = new ArrayList<IExpressionValue>();
		inputs.add(ts);
		TempNumber tn = (TempNumber)f.execute(inputs, "weight");
		System.out.println("M1 summary weight: "+tn.number);
		ts.str = "M3";
		tn = (TempNumber)f.execute(inputs, "weight");
		System.out.println("M3 summary weight: "+tn.number);
		
		//cube2 change , print after change M1 M3 summary weight
		RecipeCube cube2 = (RecipeCube) cr.getCube("ede41faf-e90b-482b-b36c-1a30dab234c2");
		TempRecipe tr ;
		RecipeItem ri1 ,ri2,ri3;
		tr = new TempRecipe();
		
		ri1 = new RecipeItem();
		ri1.Material = "M2";
		ri1.weight = 1;
		tr.root = ri1;
		
		ri2 = new RecipeItem();
		ri2.Material = "M1";
		ri2.weight = 3;
		ri2.parent = ri1;
		ri1.children.add(ri2);
		
		ri3 = new RecipeItem();
		ri3.Material = "M3";
		ri3.weight = 2;
		ri3.parent = ri1;
		ri1.children.add(ri3);
		
		cube2.content = tr;
		cr.saveCube(cube2);	
		
		System.out.println("cube2 recipe changed to M2 = 3*M1 + 2*M3!");
		
		ts.str = "M1";
		tn = (TempNumber)f.execute(inputs, "weight");
		System.out.println("M1 summary weight: "+tn.number);
		
		ts.str = "M3";
		tn = (TempNumber)f.execute(inputs, "weight");
		System.out.println("M3 summary weight: "+tn.number);
	*/	
	}
	
	public static void create()
	{
		//cube1 :  composed function: 1 input type string(material name),
		//                            1 ouput type number(summary weight) without output calculation expression
		ComposedFunctionCube cube1 = (ComposedFunctionCube) cr.createCube(ComposedFunctionCube.CUBE_TYPE);
		FunctionParameterDefinition fpdf = new FunctionParameterDefinition();
		fpdf.id = "material";
		fpdf.valueType = StringValue.EXPRESION_VALUE_TYPE;
		cube1.inputParameterDefinitions.add(fpdf);
		fpdf = new FunctionParameterDefinition();
		fpdf.id = "weight";
		fpdf.valueType = NumberValue.EXPRESION_VALUE_TYPE;
		cube1.outputParameterDefinitions.add(fpdf);
		cr.saveCube(cube1);
		
		
		TempRecipe tr ;
		RecipeItem ri1 ,ri2,ri3,ri4,ri5;
		
		//cube2  replace M2 recipe
		RecipeCube cube2 = (RecipeCube) cr.createCube(RecipeCube.CUBE_TYPE);
		
		tr = new TempRecipe();
		
		ri1 = new RecipeItem();
		ri1.Material = "M2";
		ri1.weight = 1;
		tr.root = ri1;
		
		ri2 = new RecipeItem();
		ri2.Material = "M1";
		ri2.weight = 1;
		ri2.parent = ri1;
		ri1.children.add(ri2);
		
		ri3 = new RecipeItem();
		ri3.Material = "M3";
		ri3.weight = 5;
		ri3.parent = ri1;
		ri1.children.add(ri3);
		
		cube2.content = tr;
		cr.saveCube(cube2);
		
		//cube3 : table cube , not fill children, just create columns
		LocalTableCube cube3 = (LocalTableCube) cr.createCube(LocalTableCube.CUBE_TYPE);
		
		LocalTableColumn col = null;
		col = new LocalTableColumn();
		col.isCube = false;
		col.isFomular = false;
		col.name = "Product";
		col.valueType = StringValue.EXPRESION_VALUE_TYPE;
		cube3.columns.add(col);
		
		col = new LocalTableColumn();
		col.isCube = true;
		col.isFomular = false;
		col.name = "Recipe";
		col.valueType = RecipeValue.EXPRESION_VALUE_TYPE;
		cube3.columns.add(col);
		
		col = new LocalTableColumn();
		col.isCube = true;
		col.isFomular = true;
		col.name = "Weight";
		col.valueType = NumberValue.EXPRESION_VALUE_TYPE;
		cube3.columns.add(col);
		
		cr.saveCube(cube3);
		
		//cube4 : P1 recipe
		RecipeCube cube4 = (RecipeCube) cr.createCube(RecipeCube.CUBE_TYPE);
		
		tr = new TempRecipe();
		
		ri1 = new RecipeItem();
		ri1.Material = "P1";
		ri1.weight = 1;
		tr.root = ri1;
		
		ri2 = new RecipeItem();
		ri2.Material = "M1";
		ri2.weight = 3;
		ri2.parent = ri1;
		ri1.children.add(ri2);
		
		ri3 = new RecipeItem();
		ri3.Material = "M2";
		ri3.weight = 2;
		ri3.parent = ri1;
		ri1.children.add(ri3);
		
		ri4 = new RecipeItem();
		ri4.Material = "M1";
		ri4.weight = 2;
		ri4.parent = ri3;
		ri3.children.add(ri4);
		
		ri5 = new RecipeItem();
		ri5.Material = "M3";
		ri5.weight = 3;
		ri5.parent = ri3;
		ri3.children.add(ri5);
		
		cube4.content = tr;
		cube4.parent = cube3.id;
		cr.saveCube(cube4);

		
		//cube5 : P2 recipe
		RecipeCube cube5 = (RecipeCube) cr.createCube(RecipeCube.CUBE_TYPE);
		
		tr = new TempRecipe();
		
		ri1 = new RecipeItem();
		ri1.Material = "P2";
		ri1.weight = 1;
		tr.root = ri1;
		
		ri2 = new RecipeItem();
		ri2.Material = "M1";
		ri2.weight = 1;
		ri2.parent = ri1;
		ri1.children.add(ri2);
		
		ri3 = new RecipeItem();
		ri3.Material = "M2";
		ri3.weight = 3;
		ri3.parent = ri1;
		ri1.children.add(ri3);
		
		ri4 = new RecipeItem();
		ri4.Material = "M1";
		ri4.weight = 4;
		ri4.parent = ri3;
		ri3.children.add(ri4);
		
		ri5 = new RecipeItem();
		ri5.Material = "M3";
		ri5.weight = 1;
		ri5.parent = ri3;
		ri3.children.add(ri5);
		
		cube5.content = tr;
		cube5.parent = cube3.id;
		cr.saveCube(cube5);
		
		//cube6 : P3 recipe
		RecipeCube cube6 = (RecipeCube) cr.createCube(RecipeCube.CUBE_TYPE);
		
		tr = new TempRecipe();
		
		ri1 = new RecipeItem();
		ri1.Material = "P3";
		ri1.weight = 1;
		tr.root = ri1;
		
		ri2 = new RecipeItem();
		ri2.Material = "M1";
		ri2.weight = 2;
		ri2.parent = ri1;
		ri1.children.add(ri2);
		
		ri3 = new RecipeItem();
		ri3.Material = "M2";
		ri3.weight = 1;
		ri3.parent = ri1;
		ri1.children.add(ri3);
		
		ri4 = new RecipeItem();
		ri4.Material = "M1";
		ri4.weight = 5;
		ri4.parent = ri3;
		ri3.children.add(ri4);
		
		ri5 = new RecipeItem();
		ri5.Material = "M3";
		ri5.weight = 6;
		ri5.parent = ri3;
		ri3.children.add(ri5);
		
		cube6.content = tr;
		cube6.parent = cube3.id;
		cr.saveCube(cube6);
		
		//cube7: replaced weight fomular  =  (cube3@Recipe.recipe - cube2.recipe).weight(cube1@material)
		FomularCube cube7 = (FomularCube) cr.createCube(FomularCube.CUBE_TYPE);
		
		CubeInternalVariableExpression cvexp = new CubeInternalVariableExpression();
		cvexp.cubeID = cube3.id;
		cvexp.internalVariableName = "Recipe.recipe";
		
		CubeAttributeExpression caexp = new CubeAttributeExpression();
		caexp.cubeID = cube2.id;
		caexp.attributeName = "recipe";
		
		OperatorExpression opexp = new OperatorExpression();
		opexp.leftParameter = cvexp;
		opexp.rightParameter = caexp;
		opexp.sign = "-";
		
		ExpressionAttributeExpression eaexp = new ExpressionAttributeExpression();
		eaexp.attributeName = "weight";
		eaexp.expression = opexp;
		
		CubeInternalVariableExpression cvexp1 = new CubeInternalVariableExpression();
		cvexp1.cubeID = cube1.id;
		cvexp1.internalVariableName = "material";
		
		FunctionCallExpression fcexp = new FunctionCallExpression();
		fcexp.functionExpression = eaexp;
		fcexp.inputParameters.add(cvexp1);
		fcexp.outputParameterId = "weight";
		
		cube7.exp = fcexp;
		cube7.parent = cube3.id;
		cr.saveCube(cube7);
				
		//cube3 : fill talbe rows using cube4 - cube7
		LocalTableRow row ;
		
		row = new LocalTableRow();
		row.cube = cube3;
		row.rowID = UUID.randomUUID().toString();
		TempString temp1 = new TempString();
		temp1.str = "P1";
		row.cells.put(cube3.columns.get(0), temp1);
		row.cells.put(cube3.columns.get(1), cube4);
		row.cells.put(cube3.columns.get(2), cube7);
		cube3.rows.add(row);
		
		row = new LocalTableRow();
		row.cube = cube3;
		row.rowID = UUID.randomUUID().toString();
		TempString temp2 = new TempString();
		temp2.str = "P2";
		row.cells.put(cube3.columns.get(0), temp2);
		row.cells.put(cube3.columns.get(1), cube5);
		row.cells.put(cube3.columns.get(2), cube7);
		cube3.rows.add(row);
		
		row = new LocalTableRow();
		row.cube = cube3;
		row.rowID = UUID.randomUUID().toString();
		TempString temp3 = new TempString();
		temp3.str = "P3";
		row.cells.put(cube3.columns.get(0), temp3);
		row.cells.put(cube3.columns.get(1), cube6);
		row.cells.put(cube3.columns.get(2), cube7);
		cube3.rows.add(row);
		
		cube3.parent = cube1.id;
		cr.saveCube(cube3);
		
		//fill cube1 's output calculation expression = cube3.table.summary("Weight")
		
		CubeAttributeExpression caexp1 = new CubeAttributeExpression();
		caexp1.cubeID = cube3.id;
		caexp1.attributeName = "table";
		
		ExpressionAttributeExpression eaexp2 = new ExpressionAttributeExpression();
		eaexp2.attributeName = "summary";
		eaexp2.expression = caexp1;
		
		FunctionCallExpression fcexp1 = new FunctionCallExpression();
		fcexp1.functionExpression = eaexp2;
		TempString tempstr = new TempString();
		tempstr.str = "Weight";
		ConstantExpression cexp = new ConstantExpression();
		cexp.constant = tempstr;
		fcexp1.inputParameters.add(cexp);
		fcexp1.outputParameterId = "summary";
		
		cube1.outputExpressions.put(cube1.outputParameterDefinitions.get(0), fcexp1);
		cr.saveCube(cube1);
		
		System.out.println("Test 4 create cube finished!");
	}
}
