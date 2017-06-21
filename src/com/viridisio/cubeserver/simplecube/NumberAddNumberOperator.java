package com.viridisio.cubeserver.simplecube;

import java.util.Date;
import com.viridisio.cubeserver.core.IExpressionValue;
import com.viridisio.cubeserver.core.Operator;


public class NumberAddNumberOperator extends Operator {

	@Override
	public IExpressionValue execute(IExpressionValue leftValue, IExpressionValue rightValue) {
		
		double leftNumber;
		double rightNumber;
		double resultNumber;
		
		if (leftValue instanceof NumberValue) {
			leftNumber = ((NumberValue) leftValue).getNumber();
		}else return null;
		
		if (rightValue instanceof NumberValue ) {
			rightNumber = ((NumberValue) rightValue).getNumber();
		} else return null;
		
		resultNumber = leftNumber + rightNumber;
		
		TempNumber result = new TempNumber();
		result.number = resultNumber;
		result.lastModifiedDate = new Date();
		
		return result;
	}

}
