package com.viridisio.cubeserver.simplecube;

import java.util.Date;
import com.viridisio.cubeserver.core.IConstant;

public class TempBoolean extends BooleanValue implements IConstant {

	public boolean bool;
	
	public Date lastModifiedDate;
	
	
	public TempBoolean() {
		super();
		this.lastModifiedDate = new Date();
	}

	@Override
	public Date getLastModifiedDate() {

		return this.lastModifiedDate;
	}

	@Override
	public boolean getBoolean() {

		return this.bool;
	}

	@Override
	public String saveToString() {
		if (this.bool == true)
			return "true";
		else return "false";
	}

	@Override
	public void filledFromString(String content) {
		
		if ("true".equals(content))
			this.bool = true;
		
		if ("false".equals(content))
			this.bool = false;
	}

}
