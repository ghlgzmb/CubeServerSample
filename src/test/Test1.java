package test;

import com.viridisio.cubeserver.core.ConstantExpression;
import com.viridisio.cubeserver.core.ConstantFactory;
import com.viridisio.cubeserver.core.Cube;
import com.viridisio.cubeserver.core.CubeAttributeExpression;
import com.viridisio.cubeserver.core.CubeRepository;
import com.viridisio.cubeserver.core.FomularCube;
import com.viridisio.cubeserver.core.FunctionCallExpression;
import com.viridisio.cubeserver.core.OperatorExpression;
import com.viridisio.cubeserver.javafunctioncube.JavaFunctionCube;
import com.viridisio.cubeserver.simplecube.NumberValue;
import com.viridisio.cubeserver.simplecube.SimpleCube;
import com.viridisio.cubeserver.simplecube.StringValue;

public class Test1 {

	public static CubeRepository cr = CubeRepository.getInstance();
	
	public static void main(String[] args) {
		
		//create();

		Cube cube = cr.getCube("43dd7555-f4b5-4f87-a3b0-988d7dad06de");
		
		NumberValue num = (NumberValue)cube.getAttributeValueByName("value");
		
		System.out.print(num.getNumber());

		
		

	}
	
	public static void create(){
		
		JavaFunctionCube cube1 = (JavaFunctionCube) cr.createCube(JavaFunctionCube.CUBE_TYPE);
		cube1.className = "com.viridisio.cubeserver.javafunctioncube.CurrencyExchangeFunction";
		cr.saveCube(cube1);
		
		
		SimpleCube cube2 = (SimpleCube)cr.createCube(SimpleCube.CUBE_TYPE);
		cube2.content = "3";
		cube2.contentType = SimpleCube.TYPE_NUMBER;
		cr.saveCube(cube2);
		
		
		SimpleCube cube3 = (SimpleCube)cr.createCube(SimpleCube.CUBE_TYPE);
		cube3.content = "20";
		cube3.contentType = SimpleCube.TYPE_NUMBER;
		cr.saveCube(cube3);
		
		
		FomularCube cube4 = (FomularCube)cr.createCube(FomularCube.CUBE_TYPE);
		
		//cube4 :  cube1.function("USD",TWD,(cube2.value + cube3.value))
		
		//"USD" "TWD"   2 string constant
		ConstantExpression usd = new ConstantExpression();
		usd.constant = ConstantFactory.createConstant(StringValue.EXPRESION_VALUE_TYPE, "USD");
		ConstantExpression twd = new ConstantExpression();
		twd.constant = ConstantFactory.createConstant(StringValue.EXPRESION_VALUE_TYPE, "TWD");
		
		//cube2.value
		CubeAttributeExpression c2v = new CubeAttributeExpression();
		c2v.attributeName = "value";
		c2v.cubeID = cube2.id;
		
		//cube3.value
		CubeAttributeExpression c3v = new CubeAttributeExpression();
		c3v.attributeName = "value";
		c3v.cubeID = cube3.id;
		
		//cube2.value + cube3.value
		OperatorExpression ope = new OperatorExpression();
		ope.sign = "+";
		ope.leftParameter = c2v;
		ope.rightParameter = c3v;
		
		//cube1.function
		CubeAttributeExpression c1f = new CubeAttributeExpression();
		c1f.attributeName = "function";
		c1f.cubeID = cube1.id;
		
		//cube1.function("USD",TWD,(cube2.value + cube3.value))
		FunctionCallExpression fcexp = new FunctionCallExpression();
		fcexp.functionExpression = c1f;
		fcexp.inputParameters.add(usd);
		fcexp.inputParameters.add(twd);
		fcexp.inputParameters.add(ope);
		fcexp.outputParameterId = "toNumber";
		
		//cube4 :  cube1.function("USD",TWD,(cube2.value + cube3.value))
		cube4.exp = fcexp;
		cr.saveCube(cube4);
		
		System.out.println("Test1 cubes create finished!");
	}

}
