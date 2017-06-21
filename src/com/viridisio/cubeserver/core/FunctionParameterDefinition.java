package com.viridisio.cubeserver.core;

public class FunctionParameterDefinition {

	public String id;
	
	public String valueType;

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof FunctionParameterDefinition){
			FunctionParameterDefinition pdef = (FunctionParameterDefinition)obj;
			if (this.id != null && this.id.equals(pdef))
				return true;
		}
		
		return false;
	}
	
	
}
