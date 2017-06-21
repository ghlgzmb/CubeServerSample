package com.viridisio.cubeserver.composedfuncitoncube;

import java.util.ArrayList;
import java.util.Date;

import com.viridisio.cubeserver.core.Expression;
import com.viridisio.cubeserver.core.Function;
import com.viridisio.cubeserver.core.FunctionParameterDefinition;
import com.viridisio.cubeserver.core.IExpressionValue;

public class ComposedFunction extends Function {

	public ComposedFunctionCube cube ;
	
	public Date lastModifiedDate;
	
	public ComposedFunction(ComposedFunctionCube cube) {
		super();
		this.lastModifiedDate = new Date();
		this.cube = cube;
		
		this.inputParameterDefinitions = cube.inputParameterDefinitions;
		this.outputParameterDefinitions= cube.outputParameterDefinitions;
	}

	@Override
	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	@Override
	public IExpressionValue execute(ArrayList<IExpressionValue> inputParameterValues, String outputParameterId) {
	
		if (inputParameterValues.size() == this.inputParameterDefinitions.size())
		{
			for (int i=0; i<inputParameterValues.size(); i++)
			{
				this.cube.inputExpressionValues.put(this.inputParameterDefinitions.get(i), inputParameterValues.get(i));
			}
			
			FunctionParameterDefinition fpdf = null;
			for (FunctionParameterDefinition f:this.outputParameterDefinitions)
			{
				if (f.id.equals(outputParameterId))
					fpdf = f;
			}
			
			Expression exp = this.cube.outputExpressions.get(fpdf);
			exp.calculate();
			this.cube.inputExpressionValues.clear();
			return exp.getResultValue();
			
		}
		
		return null;
	}

}
